package main

import (
	"chainmaker.org/chainmaker/contract-sdk-go/v2/pb/protogo"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sandbox"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sdk"
	"encoding/json"
	"fmt"
)

type VideoBoxContract struct {
}

func (s *VideoBoxContract) InvokeContract(method string) protogo.Response {
	switch method {
	case "cochainVideoBoxData":
		return s.cochainVideoBoxData()
	case "findByVideoBoxHash":
		return s.findByVideoBoxHash()
	case "deleteByVideoBoxHash":
		return s.deleteByVideoBoxHash()
	default:
		return sdk.Error("invalid method")
	}
}

type VideoBox struct {
	//视频盒子告警hash
	VideoBoxHash string `json:"videoBoxHash"`
	//盒子唯一标识
	BoardId string `json:"boardId"`
	//告警信息
	VideoAlarmInfor string `json:"videoAlarmInfor"`
	//上链时间
	Time string `json:"time"`
}

func NewVideoBox(videoBoxHash, boardId, videoAlarmInfor, time string) *VideoBox {
	videoBox := &VideoBox{
		VideoBoxHash:    videoBoxHash,
		BoardId:         boardId,
		VideoAlarmInfor: videoAlarmInfor,
		Time:            time,
	}
	return videoBox
}

func (s *VideoBoxContract) InitContract() protogo.Response {
	return sdk.Success([]byte("video Init Contract success"))
}

func (s *VideoBoxContract) UpgradeContract() protogo.Response {
	return sdk.Success([]byte("video Upgrade contract success"))
}

func (s *VideoBoxContract) cochainVideoBoxData() protogo.Response {
	params := sdk.Instance.GetArgs()
	//获取参数
	videoBoxHash := string(params["videoBoxHash"])
	boardId := string(params["boardId"])
	videoAlarmInfor := string(params["videoAlarmInfor"])
	time := string(params["time"])

	//构建结构体
	videoBox := NewVideoBox(videoBoxHash, boardId, videoAlarmInfor, time)

	//序列化
	videoBoxBytes, err := json.Marshal(videoBox)
	if err != nil {
		return sdk.Error(fmt.Sprintf("marshal videoBox failed,err:%s", err))
	}
	//发送事件
	sdk.Instance.EmitEvent("topic_vx", []string{videoBox.VideoBoxHash, videoBox.BoardId, videoBox.VideoAlarmInfor, videoBox.Time})

	//存储数据
	err = sdk.Instance.PutStateByte("videoBox_bytes", videoBox.VideoBoxHash, videoBoxBytes)

	//记录日志
	sdk.Instance.Infof("[cochainVideoBoxData] videoBoxHash=" + videoBox.VideoBoxHash)
	sdk.Instance.Infof("[cochainVideoBoxData] boardId=" + videoBox.BoardId)
	sdk.Instance.Infof("[cochainVideoBoxData] videoAlarmInfor=" + videoBox.VideoAlarmInfor)
	sdk.Instance.Infof("[cochainVideoBoxData] time=" + videoBox.Time)

	//返回结果
	return sdk.Success([]byte(videoBox.BoardId + videoBox.VideoAlarmInfor + videoBox.VideoBoxHash))
}

func (s *VideoBoxContract) findByVideoBoxHash() protogo.Response {
	//获取参数
	videoBoxHash := string(sdk.Instance.GetArgs()["videoBoxHash"])

	//查询结果
	result, err := sdk.Instance.GetStateByte("videoBox_bytes", videoBoxHash)
	if err != nil {
		return sdk.Error("findByVideoBoxHash failed to call get_state")
	}

	//反序列化
	var videoBox VideoBox
	if err = json.Unmarshal(result, &videoBox); err != nil {
		return sdk.Error(fmt.Sprintf("findByVideoBoxHash unmarshal videoBox failed, err: %s", err))
	}

	//返回结果
	return sdk.Success(result)
}

func (s *VideoBoxContract) deleteByVideoBoxHash() protogo.Response {
	//获取参数
	videoBoxHash := string(sdk.Instance.GetArgs()["videoBoxHash"])

	//执行删除操作
	err := sdk.Instance.DelState("videoBox_bytes", videoBoxHash)
	if err != nil {
		return sdk.Error("deleteByVideoBoxHash failed to call delete_state")
	}

	//返回结果
	return sdk.Success(nil)
}

func main() {
	err := sandbox.Start(new(VideoBoxContract))
	if err != nil {
		sdk.Instance.Errorf(err.Error())
	}
}

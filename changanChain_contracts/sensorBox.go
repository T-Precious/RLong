package main

import (
	"chainmaker.org/chainmaker/contract-sdk-go/v2/pb/protogo"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sandbox"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sdk"
	"encoding/json"
	"fmt"
)

type SensorBoxContract struct {
}

func (s *SensorBoxContract) InvokeContract(method string) protogo.Response {
	switch method {
	case "cochainSensorBoxData":
		return s.cochainSensorBoxData()
	case "findBySensorBoxHash":
		return s.findBySensorBoxHash()
	case "deleteBySensorBoxHash":
		return s.deleteBySensorBoxHash()
	default:
		return sdk.Error("invalid method")
	}
}

type SensorBox struct {
	//传感器告警hash
	SensorBoxHash string `json:"sensorBoxHash"`
	//设备Id
	DeviceId string `json:"deviceId"`
	//告警信息
	SensorAlarmInfor string `json:"sensorAlarmInfor"`
	//上链事件
	Time string `json:"time"`
}

func NewSensorBox(sensorBoxHash, deviceId, sensorAlarmInfor, time string) *SensorBox {
	sensorBox := &SensorBox{
		SensorBoxHash:    sensorBoxHash,
		DeviceId:         deviceId,
		SensorAlarmInfor: sensorAlarmInfor,
		Time:             time,
	}
	return sensorBox
}

func (s *SensorBoxContract) InitContract() protogo.Response {
	return sdk.Success([]byte("sensor Init Contract success"))
}

func (s *SensorBoxContract) UpgradeContract() protogo.Response {
	return sdk.Success([]byte("sensor Upgrade contract success"))
}

func (s *SensorBoxContract) cochainSensorBoxData() protogo.Response {
	params := sdk.Instance.GetArgs()
	//获取参数
	sensorBoxHash := string(params["sensorBoxHash"])
	deviceId := string(params["deviceId"])
	sensorAlarmInfor := string(params["sensorAlarmInfor"])
	time := string(params["time"])

	//构建结构体
	sensorBox := NewSensorBox(sensorBoxHash, deviceId, sensorAlarmInfor, time)

	//序列化
	sensorBoxBytes, err := json.Marshal(sensorBox)
	if err != nil {
		return sdk.Error(fmt.Sprintf("marshal sensorBox failed,err:%s", err))
	}
	//发送事件
	sdk.Instance.EmitEvent("topic_vx", []string{sensorBox.SensorBoxHash, sensorBox.DeviceId, sensorBox.SensorAlarmInfor, sensorBox.Time})

	//存储数据
	err = sdk.Instance.PutStateByte("sensorBox_bytes", sensorBox.SensorBoxHash, sensorBoxBytes)

	//记录日志
	sdk.Instance.Infof("[cochainSensorBoxData] sensorBoxHash=" + sensorBox.SensorBoxHash)
	sdk.Instance.Infof("[cochainSensorBoxData] deviceId=" + sensorBox.DeviceId)
	sdk.Instance.Infof("[cochainSensorBoxData] sensorAlarmInfor=" + sensorBox.SensorAlarmInfor)
	sdk.Instance.Infof("[cochainSensorBoxData] time=" + sensorBox.Time)

	//返回结果
	return sdk.Success([]byte(sensorBox.DeviceId + sensorBox.SensorAlarmInfor + sensorBox.SensorBoxHash))
}

func (s *SensorBoxContract) findBySensorBoxHash() protogo.Response {
	//获取参数
	sensorBoxHash := string(sdk.Instance.GetArgs()["sensorBoxHash"])

	//查询结果
	result, err := sdk.Instance.GetStateByte("sensorBox_bytes", sensorBoxHash)
	if err != nil {
		return sdk.Error("findBySensorBoxHash failed to call get_state")
	}

	//反序列化
	var sensorBox SensorBox
	if err = json.Unmarshal(result, &sensorBox); err != nil {
		return sdk.Error(fmt.Sprintf("findBySensorBoxHash unmarshal sensorBox failed, err: %s", err))
	}

	//返回结果
	return sdk.Success(result)
}

func (s *SensorBoxContract) deleteBySensorBoxHash() protogo.Response {
	//获取参数
	sensorBoxHash := string(sdk.Instance.GetArgs()["sensorBoxHash"])

	//执行删除操作
	err := sdk.Instance.DelState("sensorBox_bytes", sensorBoxHash)
	if err != nil {
		return sdk.Error("deleteBySensorBoxHash failed to call delete_state")
	}

	//返回结果
	return sdk.Success(nil)
}

func main() {
	err := sandbox.Start(new(SensorBoxContract))
	if err != nil {
		sdk.Instance.Errorf(err.Error())
	}
}

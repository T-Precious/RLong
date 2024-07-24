package main

import (
	"chainmaker.org/chainmaker/contract-sdk-go/v2/pb/protogo"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sandbox"
	"chainmaker.org/chainmaker/contract-sdk-go/v2/sdk"
	"encoding/json"
	"fmt"
)

type SensorBoxContract2 struct {
}

func (s *SensorBoxContract2) InvokeContract(method string) protogo.Response {
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

type SensorBox2 struct {
	//传感器告警hash
	SensorBoxHash string `json:"sensorBoxHash"`
	//设备Id
	DeviceId string `json:"deviceId"`
	//上报数据类型
	ServiceId string `json:"serviceId"`
	//上报时间
	EventTime string `json:"eventTime"`
	//告警类型
	AlarmType string `json:"alarmType"`
	//告警状态 0：恢复  1：发生
	Status string `json:"status"`
	//开始时间
	StartTime string `json:"startTime"`
	//结束时间
	EndTime string `json:"endTime"`
	//持续时长
	Duration string `json:"duration"`
	//告警信息描述
	AlarmDescrib string `json:"alarmDescrib"`
	//上链事件
	Time string `json:"time"`
}

func NewSensorBox2(sensorBoxHash, deviceId, serviceId, eventTime, alarmType, status, startTime, endTime, duration, alarmDescrib, time string) *SensorBox2 {
	sensorBox := &SensorBox2{
		SensorBoxHash: sensorBoxHash,
		DeviceId:      deviceId,
		ServiceId:     serviceId,
		EventTime:     eventTime,
		AlarmType:     alarmType,
		Status:        status,
		StartTime:     startTime,
		EndTime:       endTime,
		Duration:      duration,
		AlarmDescrib:  alarmDescrib,
		Time:          time,
	}
	return sensorBox
}

func (s *SensorBoxContract2) InitContract() protogo.Response {
	return sdk.Success([]byte("Init Contract success"))
}

func (s *SensorBoxContract2) UpgradeContract() protogo.Response {
	return sdk.Success([]byte("Upgrade contract success"))
}

func (s *SensorBoxContract2) cochainSensorBoxData() protogo.Response {
	params := sdk.Instance.GetArgs()
	//获取参数
	sensorBoxHash := string(params["sensorBoxHash"])
	deviceId := string(params["deviceId"])
	serviceId := string(params["serviceId"])
	eventTime := string(params["eventTime"])
	alarmType := string(params["alarmType"])
	status := string(params["status"])
	startTime := string(params["startTime"])
	endTime := string(params["endTime"])
	duration := string(params["duration"])
	alarmDescrib := string(params["alarmDescrib"])
	time := string(params["time"])

	//构建结构体
	sensorBox := NewSensorBox2(sensorBoxHash, deviceId, serviceId, eventTime, alarmType, status, startTime, endTime, duration, alarmDescrib, time)

	//序列化
	sensorBoxBytes, err := json.Marshal(sensorBox)
	if err != nil {
		return sdk.Error(fmt.Sprintf("marshal sensorBox failed,err:%s", err))
	}
	//发送事件
	sdk.Instance.EmitEvent("topic_vx", []string{sensorBox.SensorBoxHash, sensorBox.DeviceId, sensorBox.ServiceId, sensorBox.EventTime, sensorBox.AlarmType, sensorBox.Status, sensorBox.StartTime, sensorBox.EndTime, sensorBox.Duration, sensorBox.AlarmDescrib, sensorBox.Time})

	//存储数据
	err = sdk.Instance.PutStateByte("sensorBox_bytes", sensorBox.SensorBoxHash, sensorBoxBytes)

	//记录日志
	sdk.Instance.Infof("[cochainSensorBoxData] sensorBoxHash=" + sensorBox.SensorBoxHash)
	sdk.Instance.Infof("[cochainSensorBoxData] deviceId=" + sensorBox.DeviceId)
	sdk.Instance.Infof("[cochainSensorBoxData] serviceId=" + sensorBox.ServiceId)
	sdk.Instance.Infof("[cochainSensorBoxData] eventTime=" + sensorBox.EventTime)
	sdk.Instance.Infof("[cochainSensorBoxData] alarmType=" + sensorBox.AlarmType)
	sdk.Instance.Infof("[cochainSensorBoxData] status=" + sensorBox.Status)
	sdk.Instance.Infof("[cochainSensorBoxData] startTime=" + sensorBox.StartTime)
	sdk.Instance.Infof("[cochainSensorBoxData] endTime=" + sensorBox.EndTime)
	sdk.Instance.Infof("[cochainSensorBoxData] duration=" + sensorBox.Duration)
	sdk.Instance.Infof("[cochainSensorBoxData] alarmDescrib=" + sensorBox.AlarmDescrib)
	sdk.Instance.Infof("[cochainSensorBoxData] time=" + sensorBox.Time)

	//返回结果
	return sdk.Success([]byte(sensorBox.AlarmDescrib + sensorBox.SensorBoxHash))
}

func (s *SensorBoxContract2) findBySensorBoxHash() protogo.Response {
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

func (s *SensorBoxContract2) deleteBySensorBoxHash() protogo.Response {
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
	err := sandbox.Start(new(SensorBoxContract2))
	if err != nil {
		sdk.Instance.Errorf(err.Error())
	}
}

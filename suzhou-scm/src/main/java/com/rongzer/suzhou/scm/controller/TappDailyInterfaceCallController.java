package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.pojo.param.InterfaceUsageParam;
import com.rongzer.suzhou.scm.pojo.param.TappDailyInterfaceParam;
import com.rongzer.suzhou.scm.service.TappDailyInterfaceCallService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Description 苏州SIP信用区块链平台统一链上服务接口看板
 * @Author THINKPAD
 * @Date 2023/1/4 14:16
 * @Version 1.0
 **/
@Slf4j
@RestController
public class TappDailyInterfaceCallController {

    @Autowired
    private TappDailyInterfaceCallService tappDailyInterfaceCallService;
    /**
     * @Description:查询接口使用量统计
     * @Author: tian.changlong
     * @Date: 2023/1/4 14:18
     * @param interfaceUsageParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectTappDailyInterfaceCallByCondition")
    public String selectTappDailyInterfaceCallByCondition(@Valid @RequestBody InterfaceUsageParam interfaceUsageParam){
        ReturnResult returnResult=tappDailyInterfaceCallService.selectTappDailyInterfaceCallByCondition(interfaceUsageParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询接口使用量排行
     * @Author: tian.changlong
     * @Date: 2023/1/4 14:19
     * @return: java.lang.String
     **/
    @PostMapping("/selectAllTappDailyInterfaceCall")
    public String selectAllTappDailyInterfaceCall(){
        ReturnResult returnResult=tappDailyInterfaceCallService.selectAllTappDailyInterfaceCall();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询各部门接口使用量
     * @Author: tian.changlong
     * @Date: 2023/1/4 14:20
     * @return: java.lang.String
     **/
    @PostMapping("/selectDepartmentTappDailyInterface")
    public String selectDepartmentTappDailyInterface(){
        ReturnResult returnResult=tappDailyInterfaceCallService.selectDepartmentTappDailyInterface();
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询各时间段接口使用量
     * @Author: tian.changlong
     * @Date: 2023/1/4 14:21
     * @param tappDailyInterfaceParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectTimePeriodTappDailyInterface")
    public String selectTimePeriodTappDailyInterface(@Valid @RequestBody TappDailyInterfaceParam tappDailyInterfaceParam){
        ReturnResult returnResult=tappDailyInterfaceCallService.selectTimePeriodTappDailyInterface(tappDailyInterfaceParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    /**
     * @Description:查询业务场景各时间段接口使用量
     * @Author: tian.changlong
     * @Date: 2023/1/5 11:32
     * @param tappDailyInterfaceParam:
     * @return: java.lang.String
     **/
    @PostMapping("/selectApplicationScenarioTappDailyInterface")
    public String selectApplicationScenarioTappDailyInterface(@Valid @RequestBody TappDailyInterfaceParam tappDailyInterfaceParam){
        ReturnResult returnResult=tappDailyInterfaceCallService.selectApplicationScenarioTappDailyInterface(tappDailyInterfaceParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
}

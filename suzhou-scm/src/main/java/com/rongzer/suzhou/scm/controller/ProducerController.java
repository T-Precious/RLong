package com.rongzer.suzhou.scm.controller;

import com.rongzer.eloan.common.starter.constant.CommonConst;
import com.rongzer.eloan.common.starter.vo.ResultVo;
import com.rongzer.suzhou.scm.annotation.LimitRequest;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.pojo.param.MqDtoParam;
import com.rongzer.suzhou.scm.service.ProducerService;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/8/18 15:32
 * @Version 1.0
 **/
@RestController
@Slf4j
@RequestMapping("producer")
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    //SimpleMessageConverter 简单模式消息发送者仅支持：String、byte[]，Serializable[实现了序列化接口的对象]
    @PostMapping("/producerSendFirstExchange")
    @LimitRequest(count = 3)
    public String producerSendFirstExchange(@Valid @RequestBody MqDtoParam mqDtoParam){
        ReturnResult returnResult=producerService.producerSendFirstExchange(mqDtoParam);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }

    @GetMapping("/startOneConsumer")
    public String startOneConsumer(String switchStr){
        ReturnResult returnResult=producerService.startOneConsumer(switchStr);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    @GetMapping("/startTwoConsumer")
    public String startTwoConsumer(String switchStr){
        ReturnResult returnResult=producerService.startTwoConsumer(switchStr);
        if(returnResult.getStatus()==1){
            return new ResultVo<>(CommonConst.SUCCESS_STATUS,returnResult).toString();
        }else{
            return new ResultVo<>(CommonConst.ERROR_STATUS,returnResult).toString();
        }
    }
    @GetMapping("/cochainSwitch")
    public String cochainSwitch(String switchStr, String dateStr){
        if (StringUtil.isNotEmpty(switchStr) && StringUtil.isNotEmpty(dateStr)) {
            if (dateStr.equals(LocalDate.now().toString())) {
                Consts.COCHAIN_SWITCH.setLength(0);
                Consts.COCHAIN_SWITCH.append(switchStr);
                log.info("在" + dateStr + "修改了上链开关状态：" + switchStr);
            } else {
                return "Switch state modification failed, input date and system date are inconsistent";
            }
        } else {
            if (StringUtil.isEmpty(switchStr) && StringUtil.isEmpty(dateStr)) {
                return "COCHAIN_SWITCH(switchStr)：" + Consts.COCHAIN_SWITCH.toString();
            }
            return "Switch status and date parameters cannot be empty";
        }
        return "COCHAIN_SWITCH(switchStr)：" + Consts.COCHAIN_SWITCH.toString();
    }
}

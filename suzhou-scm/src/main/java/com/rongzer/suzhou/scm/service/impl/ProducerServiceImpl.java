package com.rongzer.suzhou.scm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rongzer.sdk.rbaas.entity.RbaasTableColumn;
import com.rongzer.suzhou.scm.constant.Consts;
import com.rongzer.suzhou.scm.pojo.param.MqDtoParam;
import com.rongzer.suzhou.scm.properties.CommonProperties;
import com.rongzer.suzhou.scm.service.ProducerService;
import com.rongzer.suzhou.scm.util.DateUtil;
import com.rongzer.suzhou.scm.vo.ReturnResult;
import com.rongzer.util.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

/**
 * @Description TODO
 * @Author THINKPAD
 * @Date 2023/8/18 17:32
 * @Version 1.0
 **/
@Slf4j
@Service
public class ProducerServiceImpl implements ProducerService{

    @Autowired
    private CommonProperties config;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private String dateFormat = "yyyy-MM-dd";

    @Override
    public ReturnResult producerSendFirstExchange(MqDtoParam mqDtoParam) {
        ReturnResult returnResult=new ReturnResult();
        try {
            if("ON".equals(Consts.COCHAIN_SWITCH.toString())) {
                String nowCochainTime="";
                if(StringUtils.isEmpty(mqDtoParam.getCochainTime())){
                    nowCochainTime = DateUtil.getCurrentDate("HH:mm:ss");
                }else{
                    nowCochainTime=mqDtoParam.getCochainTime();
                }
                if (DateUtil.belongCalendar(nowCochainTime, config.getCochainStartTime(), Consts.TODAY_FINALTIME) || DateUtil.belongCalendar(nowCochainTime, Consts.TODAY_STARTTIME, config.getCochainEndTime())) {
                    JSONObject jsonObject = mqDtoParam.getTableData();
                    if (jsonObject.size() == 0) {
                        returnResult.setStatus(0);
                        returnResult.setMsg("上链内容不能为空");
                        return returnResult;
                    }
                    String SYNC_DATE = jsonObject.getString("SYNC_DATE");
                    if (StringUtils.isEmpty(SYNC_DATE)) {
                        returnResult.setStatus(0);
                        returnResult.setMsg("SYNC_DATE数据上链日期不能为空");
                        return returnResult;
                    }
                    try {
                        LocalDateUtil.strToDate(SYNC_DATE, dateFormat);
                    } catch (Exception e) {
                        log.error("SYNC_DATE日期格式错误:", e.toString());
                        returnResult.setStatus(0);
                        returnResult.setMsg("SYNC_DATE日期格式错误");
                        return returnResult;
                    }
                    String nowTime = LocalDateUtil.dateToStr(LocalDate.now());
                    if (!SYNC_DATE.equals(nowTime)) {
                        returnResult.setStatus(0);
                        returnResult.setMsg("上链日期SYNC_DATE:" + SYNC_DATE + "和当前日期:" + nowTime + "不等");
                        return returnResult;
                    }
                    String[] tableNames = config.getTableNames().split(",");
                    if (Arrays.asList(tableNames).contains(mqDtoParam.getTableName())) {
                        returnResult.setStatus(1);
                        returnResult.setMsg("数据正在上链");
                        rabbitTemplate.convertAndSend("suzhou-first-exchange", "", mqDtoParam);
                    } else {
                        returnResult.setStatus(0);
                        returnResult.setMsg("该表" + mqDtoParam.getTableName() + "不在上链表模型");
                    }
                } else {
                    returnResult.setStatus(0);
                    returnResult.setMsg("请在规定的时间范围内进行数据上链");
                }
            }else{
                returnResult.setStatus(0);
                returnResult.setMsg("数据上链接口服务暂未开放");
            }
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("上链异常，请联系管理员！");
            log.error("上链异常:"+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult startOneConsumer(String switchStr) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        try {
            MessageListenerContainer container=rabbitListenerEndpointRegistry.getListenerContainer(Consts.CONSUMERID);
            if("ON".equals(switchStr)){
                if(container.isRunning()){
                    returnResult.setMsg("消费者001监听容器已经在运行");
                }else{
                    container.start();
                    returnResult.setMsg("消费者001监听容器正在开启");
                }
            }else if("OFF".equals(switchStr)){
                if(container.isRunning()){
                    container.stop();
                    returnResult.setMsg("消费者001监听容器正在关闭");
                }else{
                    returnResult.setMsg("消费者001监听容器已经关闭");
                }
            }
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("消费者001监听容器开关异常："+e.toString());
        }
        return returnResult;
    }

    @Override
    public ReturnResult startTwoConsumer(String switchStr) {
        ReturnResult returnResult=new ReturnResult();
        returnResult.setStatus(1);
        try {
            MessageListenerContainer container2=rabbitListenerEndpointRegistry.getListenerContainer(Consts.CONSUMERID2);
            if("ON".equals(switchStr)){
                if(container2.isRunning()){
                    returnResult.setMsg("消费者002监听容器已经在运行");
                }else{
                    container2.start();
                    returnResult.setMsg("消费者002监听容器正在开启");
                }
            }else if("OFF".equals(switchStr)){
                if(container2.isRunning()){
                    container2.stop();
                    returnResult.setMsg("消费者002监听容器正在关闭");
                }else{
                    returnResult.setMsg("消费者002监听容器已经关闭");
                }
            }
        }catch (Exception e){
            returnResult.setStatus(0);
            returnResult.setMsg("消费者002监听容器开关异常："+e.toString());
        }
        return returnResult;
    }
}

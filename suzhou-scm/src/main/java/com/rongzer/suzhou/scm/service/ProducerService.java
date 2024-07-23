package com.rongzer.suzhou.scm.service;

import com.rongzer.suzhou.scm.pojo.param.MqDtoParam;
import com.rongzer.suzhou.scm.vo.ReturnResult;

public interface ProducerService {
    ReturnResult producerSendFirstExchange(MqDtoParam mqDtoParam);
    ReturnResult startOneConsumer(String switchStr);
    ReturnResult startTwoConsumer(String switchStr);
}

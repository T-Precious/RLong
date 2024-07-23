package com.rongzer.suzhou.scm.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 发布/订阅队列(publish/subscribe)
 * @Author THINKPAD
 * @Date 2023/8/18 15:21
 * @Version 1.0
 **/
@Configuration
public class QueueAndExchangeConfig {

    //第一个队列
    @Bean("suzhouFirstQueue")
    public Queue getFirstQueue(){
        return new Queue("suzhou-first-queue");
    }

    //第二个队列
//    @Bean("suzhouSecondQueue")
//    public Queue getSecondQueue(){
//        return new Queue("suzhou-second-queue");
//    }

    //第一个交换机：suzhou-first-exchange
    @Bean("suzhouFirstExchange")
    FanoutExchange getSuzhouFirstExchange(){
        return new FanoutExchange("suzhou-first-exchange");
    }

    //将suzhou-first-queue队列绑定到suzhou-first-exchange交换机上
    @Bean
    Binding bindingFirstQueueToFanoutExchange(){
        return BindingBuilder.bind(getFirstQueue()).to(getSuzhouFirstExchange());
    }

    //将suzhou-second-queue队列绑定到suzhou-first-exchange交换机上
//    @Bean
//    Binding bindingSecondQueueToFanoutExchange(){
//        return BindingBuilder.bind(getSecondQueue()).to(getSuzhouFirstExchange());
//    }
}

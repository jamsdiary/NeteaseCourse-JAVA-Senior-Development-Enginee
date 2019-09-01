package com.study.order.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsgSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void topicSend(String context) {
        System.out.println("发送消息内容：" + context);
        this.rabbitTemplate.convertAndSend("payOrderExchange", "topic.payOrderMessage", context);
    }


}

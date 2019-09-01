package com.study.order.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class TopicReceive {

//	@RabbitListener(queues = "topic.payOrderMessage")
	public void process(String context) {
		System.out.println("队列topic.message接收到的消息：" + context);
	}

}

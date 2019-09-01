package com.study.order.service;

import com.alibaba.fastjson.JSONObject;
import com.study.order.bean.UserStudy;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicReceive {
	@Autowired
	UserStudyService userStudyService;

	@RabbitListener(queues = "topic.payOrderMessage")
	public void process(String context) {
		System.out.println("队列topic.message接收到的消息：" + context);
		UserStudy userStudy = JSONObject.parseObject(context, UserStudy.class);
		userStudyService.addUserStudy(userStudy);
	}
}

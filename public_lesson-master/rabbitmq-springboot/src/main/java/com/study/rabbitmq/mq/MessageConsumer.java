package com.study.rabbitmq.mq;

import java.io.IOException;

import com.study.rabbitmq.model.User;
import com.study.rabbitmq.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * 
 * @author allen
 */
@Component
public class MessageConsumer {
	private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	@Autowired
    UserService userService;
	
	@Autowired
	MessageProducer messageProducer;

	/**
	 * 监听方法传入的参数需要和消息生产者的一致
	 * @param message
	 */
	@RabbitListener(queues = RabbitConfig.DIRECT_ROUTING_KEY_recvqueue)	// 监听 recvqueue 队列
	@RabbitHandler	// handler注解来指定对消息的处理方法
	public void onMessage(Message message) {
		logger.info("consumer receive message------->:{}", message);
		User user = userService.getUser(new String(message.getBody()));
		try {
			messageProducer.sendMessage(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 业务消费
	 */
	@RabbitListener(queues = RabbitConfig.DIRECT_ROUTING_KEY_sendqueue)	// 监听 sendqueue 队列
	@RabbitHandler	// handler注解来指定对消息的处理方法
	public void basicMessage(Object message) {
		System.out.println(" [x] Received '" + message + "'");
	}

}

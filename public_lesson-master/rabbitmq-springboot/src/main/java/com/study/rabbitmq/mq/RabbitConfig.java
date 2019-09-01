package com.study.rabbitmq.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置一个队列，给队列起名字
 * 
 * @author allen
 */
@Configuration
public class RabbitConfig {
	
	public static final String DIRECT_ROUTING_KEY_recvqueue = "recvqueue";

	public static final String DIRECT_ROUTING_KEY_sendqueue = "sendqueue";

	// 创建队列 - 读取队列
	@Bean
	public Queue recvQueue() {
		Queue queue = new Queue(DIRECT_ROUTING_KEY_recvqueue, true);
		return queue;
	}

	// 创建队列 - 发送队列
	@Bean
	public Queue sendQueue() {
		return new Queue(DIRECT_ROUTING_KEY_sendqueue, true);
	}

	// 创建交换器
	@Bean
	public DirectExchange exchange() {
		return new DirectExchange("directExchange");
	}
	
	//对列绑定并关联到ROUTINGKEY
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(sendQueue()).to(exchange()).withQueueName();
    }
    
}

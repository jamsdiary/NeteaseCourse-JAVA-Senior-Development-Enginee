package com.study.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicRabbitConfig {
	final static String PAY_ORDER_MESSAGE = "topic.payOrderMessage";

	@Bean(name = "payOrderMessage")
	public Queue queueMessage() {
		return new Queue(TopicRabbitConfig.PAY_ORDER_MESSAGE);
	}

	@Bean
    TopicExchange exchange() {
		return new TopicExchange("payOrderExchange");
	}

	@Bean
    Binding bindingExchangeMessage(@Qualifier("payOrderMessage") Queue queueMessage, TopicExchange exchange) {
		return BindingBuilder.bind(queueMessage).to(exchange).with("topic.payOrderMessage");
	}
}

package com.study.rabbitmq.spring.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(AppConfiguration.class)
public class ProducerApp {

    private static final Logger logger = LoggerFactory.getLogger(ProducerApp.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue helloSpring;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            template.convertAndSend(helloSpring.getName(), "Hello Spring");
            logger.info("消息已发送");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }

}

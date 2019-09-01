package com.study.rabbitmq.spring.work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
@EnableAutoConfiguration
@RabbitListener(queues = "spring.work")
@Import(AppConfiguration.class)
public class ConsumerApp1 {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerApp1.class);

    @RabbitHandler
    public void receive(@Payload String msg) throws InterruptedException {
        logger.info("收到消息：" + msg);
        Thread.sleep(500);
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ConsumerApp1.class);
        springApplication.setAdditionalProfiles("work");
        springApplication.run(args);
    }
}

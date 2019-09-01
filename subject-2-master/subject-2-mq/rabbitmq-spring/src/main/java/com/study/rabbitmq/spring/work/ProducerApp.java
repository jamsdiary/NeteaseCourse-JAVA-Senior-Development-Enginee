package com.study.rabbitmq.spring.work;

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

@EnableAutoConfiguration
@Configuration
@Import(AppConfiguration.class)
public class ProducerApp {
    private static final Logger logger = LoggerFactory.getLogger(com.study.rabbitmq.spring.simple.ProducerApp.class);

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private Queue springQueue;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            for (int i = 0; i < 20; i++) {
                StringBuilder builder = new StringBuilder("Hello Spring ");
                builder.append(i);
                String message = builder.toString();

                template.convertAndSend(springQueue.getName(), message);
                logger.info("已发送：" + message);
                Thread.sleep(300);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(ProducerApp.class, args));
    }
}

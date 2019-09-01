package com.study.rabbitmq.spring.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
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
    private TopicExchange topic;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            String message = "Hello spring.red";
            template.convertAndSend(topic.getName(), "spring.red", message);
            logger.info("已发送：" + message);

            message = "Hello spring.yellow";
            template.convertAndSend(topic.getName(), "spring.yellow", message);
            logger.info("已发送：" + message);

            message = "Hello rabbit.red";
            template.convertAndSend(topic.getName(), "rabbit.red", message);
            logger.info("已发送：" + message);
        };
    }

    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(ProducerApp.class, args));
    }
}

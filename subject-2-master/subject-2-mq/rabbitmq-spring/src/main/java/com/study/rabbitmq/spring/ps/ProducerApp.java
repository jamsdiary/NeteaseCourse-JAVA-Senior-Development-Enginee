package com.study.rabbitmq.spring.ps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
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
    private FanoutExchange fanout;

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            for (int i = 0; i < 10; i++) {
                StringBuilder builder = new StringBuilder("Hello Spring ");
                builder.append(i);
                String message = builder.toString();

                template.convertAndSend(fanout.getName(), "", message);
                logger.info("已发送：" + message);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.exit(SpringApplication.run(ProducerApp.class, args));
    }
}

package com.study.rabbitmq.spring.work;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public Queue helloSpring() {
        return new Queue("spring.work");
    }

}

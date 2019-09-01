package com.study.rabbitmq.spring.routing;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("spring.direct");
    }

    @Profile("consumer")
    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    @Profile("red")
    @Bean
    public Binding binding1(DirectExchange direct, Queue queue) {
        return BindingBuilder.bind(queue).to(direct).with("red");
    }

    @Profile("yellow")
    @Bean
    public Binding binding2(DirectExchange direct, Queue queue) {
        return BindingBuilder.bind(queue).to(direct).with("yellow");
    }

}

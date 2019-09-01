package com.study.rabbitmq.spring.ps;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("spring.fanout");
    }

    @Profile("consumer")
    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    @Profile("consumer")
    @Bean
    public Binding binding(FanoutExchange fanout, Queue queue) {
        return BindingBuilder.bind(queue).to(fanout);
    }

}

package com.study.rabbitmq.spring.topic;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange("spring.topic");
    }

    @Profile("consumer")
    @Bean
    public Queue queue() {
        return new AnonymousQueue();
    }

    @Profile("#")
    @Bean
    public Binding binding1(TopicExchange topic, Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("#");
    }

    @Profile("*.red")
    @Bean
    public Binding binding2(TopicExchange topic, Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("*.red");
    }

    @Profile("spring.*")
    @Bean
    public Binding binding3(TopicExchange topic, Queue queue) {
        return BindingBuilder.bind(queue).to(topic).with("spring.*");
    }

}

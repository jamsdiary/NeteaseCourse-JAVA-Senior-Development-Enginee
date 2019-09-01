package com.study.springboot.demo1.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConditional {
    @Bean
    @ConditionalOnClass(name="org.springframework.data.redis.core.RedisTemplate")
    public Object test1(){
        System.out.println("代码中用到了redis。我的代码被执行");
        return new Object();
    }



}

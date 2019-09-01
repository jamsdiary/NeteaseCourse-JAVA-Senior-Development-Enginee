package com.study.springboot.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: allen
 * @Date: 2019/2/26 14:20
 */
@Component
public class Consumer {

    @KafkaListener(topics = {"test2","test"})
    public void processMessage(String content) {

        System.out.println("消息被消费" + content);
    }

}

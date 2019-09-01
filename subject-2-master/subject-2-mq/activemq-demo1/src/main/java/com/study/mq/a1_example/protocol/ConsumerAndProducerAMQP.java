package com.study.mq.a1_example.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// http://activemq.apache.org/amqp.html
public class ConsumerAndProducerAMQP {
    public static void main(String[] args) {
        // TODO 这个amqp 1.0的协议目前没有啥成熟的客户端，spring也暂无打算集成。
        // https://github.com/spring-projects/spring-boot/pull/9315
        // 你可以用qpid 的客户端类库 http://qpid.apache.org/proton/
        // 蜜汁蛋疼，amqp还是直接用rabbitmq吧。
    }
}

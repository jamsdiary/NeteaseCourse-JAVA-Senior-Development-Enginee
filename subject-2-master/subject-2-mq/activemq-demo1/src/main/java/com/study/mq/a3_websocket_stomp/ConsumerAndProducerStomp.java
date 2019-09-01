package com.study.mq.a3_websocket_stomp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;
import org.apache.activemq.transport.stomp.StompConnection;
import org.apache.activemq.transport.stomp.StompFrame;

import javax.jms.*;

// http://activemq.apache.org/stomp.html
public class ConsumerAndProducerStomp {
    public static void main(String[] args) throws Exception {
        // 直接用Stomp代码的方式
        ConsumerAndProducerStomp.stompTest();
    }

    public static void stompTest() throws Exception {
        StompConnection connection = new StompConnection();
        connection.open("activemq.tony.com", 61613);

        connection.connect("system", "manager");

        // 发送两条数据
        connection.begin("tx1");
        connection.send("/topic/test-stomp", "message1");
        connection.send("/topic/test-stomp", "message2");
        connection.commit("tx1");

        // 订阅/topic/test-stomp
        connection.subscribe("/topic/test-stomp", Stomp.Headers.Subscribe.AckModeValues.CLIENT);

        connection.begin("tx2");
        // 接收数据并打印
        StompFrame message = connection.receive();
        System.out.println(message.getBody());
        connection.ack(message, "tx2");
        // 继续接收
        message = connection.receive();
        System.out.println(message.getBody());
        connection.ack(message, "tx2");

        connection.commit("tx2");
        connection.disconnect();
    }
}

package com.study.mq.a1_example.helloworld.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 非持久订阅者
// 非持久订阅只有当客户端处于连接状态才能收到发送到某个主题的消息，
// 而当客户端处于离线状态，这个时间段发到主题的消息它永远不会收到
public class Consumer {
    public static void main(String[] args) {
        new ConsumerThread("tcp://activemq.tony.com:61616", "topic1").start();
        new ConsumerThread("tcp://activemq.tony.com:61616", "topic1").start();
    }

}

class ConsumerThread extends Thread {

    String brokerUrl;
    String destinationUrl;

    public ConsumerThread(String brokerUrl, String destinationUrl) {
        this.brokerUrl = brokerUrl;
        this.destinationUrl = destinationUrl;
    }

    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn;
        Session session;
        MessageConsumer consumer;

        try {
            // brokerURL http://activemq.apache.org/connection-configuration-uri.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(this.brokerUrl);
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start(); // 一定要启动
            // 3、创建会话（可以创建一个或者多个session）
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建订阅的目标 topic  一条消息，多个订阅者接收
            Destination destination = session.createTopic(destinationUrl);

            // 5、创建消费者消息 http://activemq.apache.org/destination-options.html
            consumer = session.createConsumer(destination);

            // 6、接收消息(没有消息就持续等待)
            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) message).getText());
            } else {
                System.out.println(message);
            }

            consumer.close();
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

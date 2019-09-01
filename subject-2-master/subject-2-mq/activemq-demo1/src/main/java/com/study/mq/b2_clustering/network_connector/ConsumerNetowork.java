package com.study.mq.b2_clustering.network_connector;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// http://activemq.apache.org/networks-of-brokers.html
public class ConsumerNetowork {
    public static void main(String[] args) throws InterruptedException {
        // 消费者用192.168,100.242
        String brokerUrl = "failover:(tcp://192.168.100.242:61616)?initialReconnectDelay=100";
        ConsumerThread queue1 = new ConsumerThread(brokerUrl, "queue1");
        queue1.start();
        queue1.join();
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
            // 4、创建点对点接收的目标，queue - 点对点
            Destination destination = session.createQueue(destinationUrl);

            // 5、创建消费者消息 http://activemq.apache.org/destination-options.html
            consumer = session.createConsumer(destination);

            // 6、接收消息
            consumer.setMessageListener(message -> {
                try {
                    if (message instanceof TextMessage) {
                        System.out.println("收到文本消息：" + ((TextMessage) message).getText());
                    } else {
                        System.out.println(message);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}



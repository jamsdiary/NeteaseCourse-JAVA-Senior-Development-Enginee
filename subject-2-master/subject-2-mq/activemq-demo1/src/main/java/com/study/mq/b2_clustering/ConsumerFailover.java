package com.study.mq.b2_clustering;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// http://activemq.apache.org/failover-transport-reference.html
public class ConsumerFailover {
    public static void main(String[] args) throws InterruptedException {
        // 非failover的公共参数配置通过nested.*，例如 failover:(...)?nested.wireFormat.maxInactivityDuration=1000
        // ?randomize=false 随机选择，默认是顺序
        // 指定优先切换 failover:(tcp://host1:61616,tcp://host2:61616,tcp://host3:61616)?priorityBackup=true&priorityURIs=tcp://local1:61616,tcp://local2:61616
        // maxReconnectDelay重连的最大间隔时间(毫秒)
        String brokerUrl = "failover:(tcp://activemq.tony.com:61616,tcp://activemq-slave.tony.com:61616)?initialReconnectDelay=100";
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



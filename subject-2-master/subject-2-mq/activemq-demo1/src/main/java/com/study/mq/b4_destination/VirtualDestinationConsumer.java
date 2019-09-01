package com.study.mq.b4_destination;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

/**
 * 虚拟目标
 *
 * 通过此示例演示通过虚拟目标实现持久订阅的功能，
 */
public class VirtualDestinationConsumer {
    public static void main(String[] args) {
        // brokerUrl参数： http://activemq.apache.org/connection-configuration-uri.html
        String brokerUrl = "tcp://192.168.100.242:61616";

        new ConsumerThread("c1", brokerUrl, "VTC.c1.FOO.B").start();
        new ConsumerThread("c2", brokerUrl, "VTC.c2.FOO.B").start();
    }

    static class ConsumerThread extends Thread {

        String brokerUrl;
        String destinationUrl;

        public ConsumerThread(String name, String brokerUrl, String destinationUrl) {
            super(name);
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
                // 4、创建接收消息的目标
                Destination destination = session.createQueue(destinationUrl);
                // 5、创建订阅者
                consumer = session.createConsumer(destination);

                // 6、接收消息
                consumer.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        if (message instanceof TextMessage) {
                            try {
                                System.out.println(getName() + "收到文本消息：" + ((TextMessage) message).getText());
                            } catch (JMSException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println(message);
                        }
                    }
                });

                System.out.println(getName() + " 已启动！");
                new CountDownLatch(1).await();

                consumer.close();
                session.close();
                conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

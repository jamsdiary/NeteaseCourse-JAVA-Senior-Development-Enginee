package com.study.mq.b4_destination;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class Consumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // brokerURL http://activemq.apache.org/connection-configuration-uri.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.100.242:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection("admin", "admin");
            conn.start();

            // 3、创建会话
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第一个参数为false时，第二个参数的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4、创建点对点接收的目标
//            Destination destination = session.createQueue("FOO.A");
            // 创建订阅的目标
            Destination destination = session.createTopic("NOTIFY.FOO.A");

            // 5、创建消费者消息 http://activemq.apache.org/destination-options.html
            consumer = session.createConsumer(destination);

            // 6、接收消息
            Session finalSession = session;
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            System.out.println("收到文本消息：" + ((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(message);
                    }
                }
            });

            System.out.println("消费者已启动！");
            System.in.read();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

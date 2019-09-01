package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消费分组--生产者
 *
 * 通过message.setStringProperty("JMSXGroupID","GroupA");可以创建一个分组。
 * 同一个组的消息，只会发送到同一个消费者，直到Consumer被关闭
 *
 */
public class MessageGroupProducer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn;
        Session session;

        try {
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.100.242:61616");
            // 2、创建连接对象md
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、创建会话
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建发布的目标 topic
            Destination destination = session.createQueue("queue1");
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选 持久化 / 不持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            for (int i = 0; i < 10; i++) {
                // 6、创建一条文本消息
                TextMessage message = session.createTextMessage("Hello world groupA " + i);
                message.setStringProperty("JMSXGroupID", "GroupA");
                if (i == 5) {
                    // 将JMSXGroupSeq设置为-1后，GroupA将关闭，下次再发送GroupA的消息后，将会是另一个消费者接收
                    message.setIntProperty("JMSXGroupSeq", -1);
                }
                // 7、发送消息
                producer.send(message);

                message = session.createTextMessage("Hello world groupB " + i);
                message.setStringProperty("JMSXGroupID", "GroupB");
                // 7、发送消息
                producer.send(message);

                System.out.println("消息 " + i + " 已发送");
            }
            // 8、 关闭连接
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}

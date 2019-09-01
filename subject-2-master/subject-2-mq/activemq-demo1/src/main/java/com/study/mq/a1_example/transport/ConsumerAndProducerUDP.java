package com.study.mq.a1_example.transport;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// UDP示例 http://activemq.apache.org/udp-transport-reference.html
public class ConsumerAndProducerUDP {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("udp://activemq.tony.com:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection("admin", "admin");
            conn.start();

            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4、创建点对点接收的目标
            Destination destination = session.createQueue("queue1");

            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            // DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            // DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // 6、创建一条消息
            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);
            // 7、发送消息
            producer.send(message);

            // 8、创建消费者消息
            consumer = session.createConsumer(destination);

            // 9、接收消息
            Message consumerMessage = consumer.receive();
            if (consumerMessage instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) consumerMessage).getText());
            } else {
                System.out.println(consumerMessage);
            }
        } catch (JMSException e) {
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

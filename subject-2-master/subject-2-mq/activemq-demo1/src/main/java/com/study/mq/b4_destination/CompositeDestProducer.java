package com.study.mq.b4_destination;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 复合目标
 *
 * 如果需要将一条消息发送到多个不同的目标，可以在创建目标时，通过","来分隔多个目标。
 * 例如：queue1,queue2,queue3
 *
 * 如果需要匹配目标类型，可以通过增加topic://或queue://前缀来实现。例如在队列上发送消息，同时也发送到主题上：
 * queue1,topic://topic1
 */
public class CompositeDestProducer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;

        try {
            // 1、创建连接工厂
//            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "udp://vm1.tony.com:61616");
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.100.242:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start();

            // 3、创建会话
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第一个参数为false时，第二个参数的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4、创建复合目标，消息将同时发送到FOO.A队列和FOO.B主题上
            Destination destination = session.createQueue("FOO.A,topic://FOO.B");
            // 创建发布的目标
//            Destination b4_destination = session.createTopic("topic1");

            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            // DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            // DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // 6、创建一条消息
            // 有6中消息类型：
            // BytesMessage  用来传递字节
            // MapMessage    用来传递字节
            // ObjectMessage 用来传递序列化对象
            // StreamMessage 用来传递文件等
            // TextMessage   用来传递字符串
            String text = "Hello world! ";
            TextMessage message = session.createTextMessage(text);

            // 7、发送消息
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

package com.study.mq.b5_dispatching;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 异步发送消息
 * <p>
 * ActiveMQ支持以同步或异步模式向broker发送消息。
 * 使用的模式对发送的延迟有很大影响。由于延迟通常是生产者可以实现的吞吐量的一个重要因素，因此使用异步发送可以显着提高系统性能。
 * ActiveMQ在几种情况下默认以异步模式发送消息。只有在JMS规范要求使用同步发送的情况下，才会默认同步发送。
 * <p>
 * 如果不使用事务并发送持久消息，则每次发送都将同步并阻塞，直到broker将消息安全持久存储到磁盘的确认发送回生产者为止。
 * 这个ack提供了消息不会丢失的保证，但是由于客户端被阻塞，它会造成巨大的延迟损失。
 * <p>
 * 使用方式:
 * 1. 在连接字符串上通过jms.useAsyncSend参数开启/关闭
 * 2. 通过连接工厂对象或连接对象的setUseAsyncSend方法开启/关闭
 *
 * 测试结果：
 * 1、不开启事务发送持久化消息，关闭异步发送，结果：60秒+/10W
 * 2、不开启事务发送持久化消息，开启异步发送，结果：5~20秒/10W
 * 3、不开启事务发送非持久化消息，关闭和开启异步发送，结果都是：1~20秒/10W
 * 4、开启事务发送非持久化消息，关闭和关闭异步发送，结果：2~15秒/10W
 * 5、开启事务发送持久化消息，开启/关闭异步发送，结果：60秒+/10W
 */
public class AsyncProducer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;

        try {
            // 1、创建连接工厂,可以在链接字符串中开启/关闭异步发送
//            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "udp://vm1.tony.com:61616");
            connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.100.242:61616?jms.useAsyncSend=false");

            // 或者在连接工厂上设置异步发送
//            connectionFactory.setUseAsyncSend(true);

            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            // 也可以在链接上设置异步发送
//            ((ActiveMQConnection) conn).setUseAsyncSend(true);
            conn.start();

            // 3、创建会话
            // 第一个参数:是否支持事务，如果为true，则会忽略第二个参数，被jms服务器设置为SESSION_TRANSACTED
            // 第一个参数为false时，第二个参数的值可为Session.AUTO_ACKNOWLEDGE，Session.CLIENT_ACKNOWLEDGE，DUPS_OK_ACKNOWLEDGE其中一个。
            // Session.AUTO_ACKNOWLEDGE为自动确认，客户端发送和接收消息不需要做额外的工作。哪怕是接收端发生异常，也会被当作正常发送成功。
            // Session.CLIENT_ACKNOWLEDGE为客户端确认。客户端接收到消息后，必须调用javax.jms.Message的acknowledge方法。jms服务器才会当作发送成功，并删除消息。
            // DUPS_OK_ACKNOWLEDGE允许副本的确认模式。一旦接收方应用程序的方法调用从处理消息处返回，会话对象就会确认消息的接收；而且允许重复确认。
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4、创建点对点发送的目标
            Destination destination = session.createQueue("queue2");
            // 创建发布的目标
//            Destination b4_destination = session.createTopic("topic1");

            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            // DeliveryMode.PERSISTENT 当activemq关闭的时候，队列数据将会被保存
            // DeliveryMode.NON_PERSISTENT 当activemq关闭的时候，队列里面的数据将会被清空
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            double start = System.nanoTime();
            for (int i = 1; i <= 1000000; i++) {
                // 6、创建一条消息
                // 有6中消息类型：
                // BytesMessage  用来传递字节
                // MapMessage    用来传递字节
                // ObjectMessage 用来传递序列化对象
                // StreamMessage 用来传递文件等
                // TextMessage   用来传递字符串
                String text = "Hello world! " + i;
                TextMessage message = session.createTextMessage(text);

                // 7、发送消息
                producer.send(message);

                if (i != 0 && i % 100000 == 0) {
//                    session.commit();
                    double end = System.nanoTime();
                    System.out.printf("每10W耗时：%f 秒%n", (end - start) / 1000000 / 1000);
                    start = System.nanoTime();
                }
            }
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

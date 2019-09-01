package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 消息过滤
 *
 * 消费者接收消息时，支持根据消息属性进行条件过滤，条件为字符串，语法和SQL的where条件类似
 */
public class SelectorsConsumer {

    public static void main(String[] args) throws InterruptedException {
        final String brokerUrl = "tcp://192.168.100.242:61616";
        final String queue = "queue2";

        Runnable consumer1 = new Runnable() {
            @Override
            public void run() {
                receive(brokerUrl, queue, "age >= 18 and gender = '女'");
            }
        };
        Runnable consumer2 = new Runnable() {
            @Override
            public void run() {
                receive(brokerUrl, queue, "gender = '男'");
            }
        };

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                send(brokerUrl, queue);
            }
        };

        // 启动消费者
        new Thread(consumer1, "C1").start();
        new Thread(consumer2, "C2").start();
        TimeUnit.SECONDS.sleep(5);

        // 生产者开始发送消息
        new Thread(producer).start();
    }

    /**
     * 发送消息
     */
    public static void send(String brokerUrl, String queue) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn;
        Session session;

        try {
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
            // 2、创建连接对象md
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、创建会话
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建发布的目标 topic
            Destination destination = session.createQueue(queue);
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选 持久化 / 不持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            String[] gender = new String[]{"男", "女"};
            for (int i = 0; i < 10; i++) {
                // 6、创建一条文本消息
                String text = "Hello " + i;
                TextMessage message = session.createTextMessage(text);

                // 设置消息属性
                message.setIntProperty("age", new Random().nextInt(30));
                message.setStringProperty("gender", gender[i % gender.length]);

                // 7、发送消息
                producer.send(message);
            }

            // 8、 关闭连接
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收消息
     */
    public static void receive(String brokerUrl, String queue, String selector) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;
        String name = Thread.currentThread().getName();

        try {
            // brokerURL http://activemq.apache.org/consumer-dispatch-async.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            // 2、创建连接对象
            conn = connectionFactory.createConnection();

            // 3、启动连接
            conn.start(); // 一定要启动

            // 4、创建会话（可以创建一个或者多个session）
            // 确认模式设置为客户端手动确认
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 5、创建点对点接收的目标，即接收哪个队列的消息
            // http://activemq.apache.org/destination-options.html
            Destination destination = session.createQueue(queue);

            // 6、创建消费者消息
            consumer = session.createConsumer(destination, selector);

            // 7、监听消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            System.out.println(name + " 收到文本消息：" + ((TextMessage) message).getText() + "，age：" + message.getIntProperty("age") + "，gender：" + message.getStringProperty("gender"));
                        } else {
                            System.out.println(name + " " + message);
                        }
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println(name + " 已启动，等待接收消息！");
            // 阻塞程序
            new CountDownLatch(1).await();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

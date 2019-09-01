package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 消息追溯
 * 使消费者可以接收到历史的topic消息，需要在activemq.xml中给主题设置恢复策略配置
 *
// <broker>
//    <destinationPolicy>
//        <policyMap>
//            <policyEntries>
//                <policyEntry topic=">">
//                    <!-- http://activemq.apache.org/subscription-recovery-policy.html -->
//                    <subscriptionRecoveryPolicy>
//                        <!--
//                        FixedSizedSubscriptionRecoveryPolicy: 保存一定size的消息，broker将为此Topic开辟定额的RAM用来保存
//                        最新的消息。使用maximumSize属性指定保存的size数量
//                        FixedCountSubscriptionRecoveryPolicy: 保存一定条数的消息。 使用maximumSize属性指定保存的size数量
//                        LastImageSubscriptionRecoveryPolicy: 只保留最新的一条数据
//                        QueryBasedSubscriptionRecoveryPolicy: 符合置顶selector的消息都将被保存，具体能够“恢复”多少消息
//                        ，由底层存储机制决定；比如对于非持久化消息，只要内存中还
//                        存在，则都可以恢复。
//                        TimedSubscriptionRecoveryPolicy: 保留最近一段时间的消息。使用recoverDuration属性指定保存时间 单位
//                        毫秒
//                        NoSubscriptionRecoveryPolicy: 关闭“恢复机制”。默认值。
//                        -->
//                        <!--恢复最近30分钟内的信息-->
//                        <timedSubscriptionRecoveryPolicy recoverDuration="1800000"/>
//                    </subscriptionRecoveryPolicy>
//    ...
// </broker>
 * <p>
 * 在创建主题时，传入consumer.retroactive=true参数，即可接收到所有满足恢复策略的历史消息
 */

public class RetroactiveConsumer {

    public static void main(String[] args) throws InterruptedException {
        final String brokerUrl = "tcp://192.168.100.242:61616";
        final String topic = "topic1";
        CountDownLatch cd = new CountDownLatch(1);

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                receive(brokerUrl, topic);
            }
        };

        Runnable producer = new Runnable() {
            @Override
            public void run() {
                send(brokerUrl, topic);
                System.out.println("生产者消息发送完毕！");
                cd.countDown();
            }
        };

        // 生产者开始发送消息
        new Thread(producer).start();

        // 等待生产者消息发送完后，再启动消费者
        cd.await();
        System.out.println();
        TimeUnit.SECONDS.sleep(2);
        new Thread(consumer, "C1").start();
        new Thread(consumer, "C2").start();
    }

    /**
     * 发送消息
     */
    public static void send(String brokerUrl, String topic) {
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
            Destination destination = session.createTopic(topic);
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选 持久化 / 不持久化
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 6、创建一条文本消息
            String text = "Hello world! " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date());
            TextMessage message = session.createTextMessage(text);
            for (int i = 0; i < 1; i++) {
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
    public static void receive(String brokerUrl, String topic) {
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

            // 5、创建订阅的主题
            // http://activemq.apache.org/destination-options.html
            Topic destination = session.createTopic(topic + "?consumer.retroactive=true");

            // 6、创建消费者消息
            consumer = session.createConsumer(destination);

            // 7、监听消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            System.out.println(name + " 收到文本消息：" + ((TextMessage) message).getText());
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

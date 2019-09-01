package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

/**
 * 优先级消费
 * <p>
 * ActiveMQ支持消费者优先级
 * 优先级值的范围是：0-127。最高优先级是127。默认优先级是0
 */
public class ConsumerPriority {

    public static void main(String[] args) {
        new Thread("c1") {
            @Override
            public void run() {
                receive(20);
            }
        }.start();

        new Thread("c2") {
            @Override
            public void run() {
                receive(0);
            }
        }.start();
    }

    public static void receive(int priority) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;
        String brokerUrl = "tcp://192.168.100.242:61616";
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
            session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // 5、创建点对点接收的目标，即接收哪个队列的消息
            // 在队列上设置consumer.proiority
            // http://activemq.apache.org/destination-options.html
            Destination destination;
            if (priority == 0) {
                // 当低优先级的消费者开启了独占后，此队列的其它消费者的优先级再高都不会收到消息
                destination = session.createQueue("queue1?consumer.priority=" + priority + "&consumer.exclusive=true");
            } else {
                destination = session.createQueue("queue1?consumer.priority=" + priority);
            }

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
                        // 8、确认收到消息
                        message.acknowledge();
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

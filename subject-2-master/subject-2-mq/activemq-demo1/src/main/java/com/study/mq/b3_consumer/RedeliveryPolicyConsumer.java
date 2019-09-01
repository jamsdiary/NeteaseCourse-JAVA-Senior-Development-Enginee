package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

/**
 * 消息重发
 */
public class RedeliveryPolicyConsumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;
        String brokerUrl = "tcp://192.168.100.242:61616";
        String name = Thread.currentThread().getName();

        try {
            // 创建队列重发策略
            RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
            queuePolicy.setInitialRedeliveryDelay(0); // 初始重发延迟时间，单位：毫秒
            queuePolicy.setRedeliveryDelay(5000); // 第一次以后的延迟时间
            queuePolicy.setUseExponentialBackOff(false);// 是否以指数递增的方式增加超时时间
            queuePolicy.setMaximumRedeliveries(3); // 最大重发次数，从0开始计数，为-1则不使用最大次数

            // brokerURL http://activemq.apache.org/consumer-dispatch-async.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            // 设置重发策略
            connectionFactory.setRedeliveryPolicy(queuePolicy);

            // 2、创建连接对象
            conn = connectionFactory.createConnection();

            // 3、启动连接
            conn.start(); // 一定要启动

            // 4、创建会话（可以创建一个或者多个session）
            // 确认模式设置为客户端手动确认
            session = conn.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // 5、创建点对点接收的目标，即接收哪个队列的消息
            // http://activemq.apache.org/destination-options.html
            Destination destination = session.createQueue("queue2");

            // 6、创建消费者消息
            consumer = session.createConsumer(destination);

            // 7、监听消息
            Session finalSession = session;
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        // 模拟消费者异常
                        if (((TextMessage) message).getText().endsWith("4")) {
                            throw new RuntimeException("消息重发");
                        }

                        if (message instanceof TextMessage) {
                            System.out.println(name + " 收到文本消息：" + ((TextMessage) message).getText());
                        } else {
                            System.out.println(name + " " + message);
                        }
                        // 8、确认收到消息
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        try {
                            // 消息重发
                            finalSession.recover();
                        } catch (JMSException e1) {
                            e1.printStackTrace();
                        }
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

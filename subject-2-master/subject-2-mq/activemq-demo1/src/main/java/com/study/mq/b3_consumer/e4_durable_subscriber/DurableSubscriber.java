package com.study.mq.b3_consumer.e4_durable_subscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// 永久订阅者
// 持久订阅时，客户端向JMS 注册一个识别自己身份的ID（clientId必须有）
// 当这个客户端处于离线时，JMS Provider 会为这个ID 保存所有发送到主题的消息
// 当客户再次连接到JMS Provider时，会根据自己的ID 得到所有当自己处于离线时发送到主题的消息。
// tips： ActiveMQ.Advisory开头的消息是activemq提供的一个管理消息推送
// http://activemq.apache.org/advisory-message.html
// 虚拟主题：https://www.cnblogs.com/jiangxiaoyaoblog/p/5659734.html
// http://activemq.apache.org/what-is-the-difference-between-a-virtual-topic-and-a-composite-destination.html
public class DurableSubscriber {
    public static void main(String[] args) {
        // brokerUrl参数： http://activemq.apache.org/connection-configuration-uri.html
        // 持久订阅者上生效
        // optimizedMessageDispatch 使用更大的预取限制 true
        new ConsumerThread("tcp://activemq.tony.com:61616?jms.clientID=x", "topic2").start();
        new ConsumerThread("tcp://activemq.tony.com:61616?jms.clientID=y", "topic2").start();
    }

    static class ConsumerThread extends Thread {

        String brokerUrl;
        String destinationUrl;

        public ConsumerThread(String brokerUrl, String destinationUrl) {
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
                // 4、创建订阅的目标 topic  一条消息，多个订阅者接收
                Topic topic = session.createTopic(destinationUrl);
                // 5、创建订阅者
                consumer = session.createDurableSubscriber(topic, "xxx");

                // 6、接收消息(没有消息就持续等待)
                consumer.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            if (message instanceof TextMessage) {
                                System.out.println("收到文本消息：" + ((TextMessage) message).getText());
                            } else {
                                System.out.println(message);
                            }
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                });

                System.out.println(" 已启动，等待接收消息！");
                System.in.read();

                consumer.close();
                session.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

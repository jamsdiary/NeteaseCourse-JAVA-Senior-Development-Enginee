package com.study.mq.b3_consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import javax.jms.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 消费者异步调度
 * 从ActiveMQ v4开始，消费者异步调度的配置更加灵活，可以在连接URI、Connection和ConnectionFactory上进行配置，而在以前的版本中，只能在broker服务器上配置。<br/>
 * 可以在broker的配置中，通过disableAsyncDispatch属性禁用transportConnector上的异步调度，禁用这个传输连接后，在客户端将无法开启。
 * <transportConnector name="openwire" uri="tcp://0.0.0.0:61616" disableAsyncDispatch="true"/>
 *
 * 通过这种灵活的配置，可以实现为较慢的消费者提供异步消息传递，而为较快的消费者提供同步消息传递。<br/>
 * 使用同步消息的缺点是：如果向较慢的消费者发送消息时，可能造成生产者阻塞。
 */
public class ConsumerDispatchAsync {

    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;
        String brokerUrl = "tcp://192.168.100.242:61616";

        try {
            // brokerURL http://activemq.apache.org/consumer-dispatch-async.html
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

            // 在连接工厂设置是否异步分发，作用于通过此工厂创建的所有连接
//            connectionFactory.setDispatchAsync(false);

            // 2、创建连接对象
            conn = connectionFactory.createConnection();

            // 在连接上设置是否异步分发，作用于通过此链接创建的所有session
//            ((ActiveMQConnection) conn).setDispatchAsync(false);

            // 3、启动连接
            conn.start(); // 一定要启动

            // 4、创建会话（可以创建一个或者多个session）
            // 确认模式设置为客户端手动确认
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 5、创建点对点接收的目标，即接收哪个队列的消息
            // 在队列上设置consumer.dispatchAsync是否异步分发，将仅作用于此队列
            // http://activemq.apache.org/destination-options.html
            Destination destination = session.createQueue("queue1?consumer.dispatchAsync=false");

            // 6、创建消费者消息
            consumer = session.createConsumer(destination);

            // 7、监听消息
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

package com.study.mq.b1_message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

// 延时、调度消息 http://activemq.apache.org/delay-and-schedule-message-delivery.html
// 定时发送邮件通知，或者触发代码执行
public class DelayScheduleMessageDemo {
    public static void main(String[] args) {
        new ProducerThread("tcp://activemq.tony.com:61616", "queue1").start();
    }

    static class ProducerThread extends Thread {
        String brokerUrl;
        String destinationUrl;

        public ProducerThread(String brokerUrl, String destinationUrl) {
            this.brokerUrl = brokerUrl;
            this.destinationUrl = destinationUrl;
        }

        @Override
        public void run() {
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
                // 4、创建点对点发送的目标
                Destination destination = session.createQueue(destinationUrl);
                // 5、创建生产者消息
                MessageProducer producer = session.createProducer(destination);
                // 设置生产者的模式，有两种可选 持久化 / 不持久化
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
                // 6、示例消息
                //  延时 5秒
                TextMessage message = session.createTextMessage("Hello world - 1!");
                message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5 * 1000L);

                //  延时 5秒，投递3次，间隔10秒 (投递次数=重复次数+默认的一次)
                TextMessage message2 = session.createTextMessage("Hello world - 2!");
                message2.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 5 * 1000L); // 延时
                message2.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 2 * 1000L); // 投递间隔
                message2.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 2); // 重复次数

                //  CRON 表达式的方式 以及 和上面参数的组合
                TextMessage message3 = session.createTextMessage("Hello world - 3!");
                message3.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, "0 * * * *");

                // 7、发送消息
                producer.send(message);
                producer.send(message2);
                producer.send(message3);


                // 8、 关闭连接
                session.close();
                conn.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}

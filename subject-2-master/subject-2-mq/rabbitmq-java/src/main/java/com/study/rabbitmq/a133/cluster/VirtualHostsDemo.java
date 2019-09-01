package com.study.rabbitmq.a133.cluster;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * vhost和权限应用示例
 * <p>
 * 说明：先阅读文档中的使用示例，创建号用户名和vhost，分配好权限。
 * http://code.study.com/MQ/rabbitmq/rabbitmq/blob/master/java/src/main/java/com/study/rabbitmq/a133/cluster/readme.md#vhost使用示例
 * <p>
 * 另外需要自己在管理界面创建queue2队列和test交换器
 */
public class VirtualHostsDemo {

    public static void main(String[] args) {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setUsername("order-user");
        factory.setPassword("order-user");
        factory.setVirtualHost("v1");

        Connection connection = null;
        Channel prducerChannel = null;
        Channel consumerChannel = null;

        // 3、设置每个节点的链接地址和端口
        Address[] addresses = new Address[]{
                new Address("192.168.100.242", 5672),
                new Address("192.168.100.241", 5672)
        };

        try {
            // 4、从连接工厂获取连接
            connection = factory.newConnection(addresses, "消费者");

            // 5、从链接中创建通道
            prducerChannel = connection.createChannel();

            prducerChannel.exchangeDeclare("test-exchange", "fanout");
            prducerChannel.queueDeclare("queue1", false, false, false, null);
            prducerChannel.queueBind("queue1", "test-exchange", "xxoo");
            // 消息内容
            String message = "Hello A";
            prducerChannel.basicPublish("test-exchange", "c1", null, message.getBytes());

            consumerChannel = connection.createChannel();
            // 创建一个消费者对象
            Consumer consumer = new DefaultConsumer(consumerChannel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("收到消息：" + new String(body, "UTF-8"));
                }
            };
            consumerChannel.basicConsume("queue1", true, consumer);

            System.out.println("等待接收消息");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 9、关闭通道
            if (prducerChannel != null && prducerChannel.isOpen()) {
                try {
                    prducerChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }

            // 10、关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

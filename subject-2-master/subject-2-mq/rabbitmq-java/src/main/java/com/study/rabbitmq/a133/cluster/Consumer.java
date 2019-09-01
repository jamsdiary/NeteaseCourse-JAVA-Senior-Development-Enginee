package com.study.rabbitmq.a133.cluster;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * 客户端连接集群示例
 */
public class Consumer {

    public static void main(String[] args) {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setUsername("order-user");
        factory.setPassword("order-user");
        factory.setVirtualHost("v1");

        Connection connection = null;
        Channel channel = null;

        // 3、设置每个节点的链接地址和端口
        Address[] addresses = new Address[]{
                new Address("192.168.100.242", 5672),
                new Address("192.168.100.241", 5672)
        };

        try {
            // 开启/关闭连接自动恢复，默认是开启状态
            factory.setAutomaticRecoveryEnabled(true);

            // 设置每100毫秒尝试恢复一次，默认是5秒：com.rabbitmq.client.ConnectionFactory.DEFAULT_NETWORK_RECOVERY_INTERVAL
            factory.setNetworkRecoveryInterval(100);

            // 4、从连接工厂获取连接
            connection = factory.newConnection(addresses, "消费者");

            // 添加重连监听器
            ((Recoverable) connection).addRecoveryListener(new RecoveryListener() {
                /**
                 * 重连成功后的回调
                 * @param recoverable
                 */
                public void handleRecovery(Recoverable recoverable) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date()) + " 已重新建立连接！");
                }

                /**
                 * 开始重连时的回调
                 * @param recoverable
                 */
                public void handleRecoveryStarted(Recoverable recoverable) {
                    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date()) + " 开始尝试重连！");
                }
            });

            // 5、从链接中创建通道
            channel = connection.createChannel();

            /**
             * 6、声明（创建）队列
             * 如果队列不存在，才会创建
             * RabbitMQ 不允许声明两个队列名相同，属性不同的队列，否则会报错
             *
             * queueDeclare参数说明：
             * @param queue 队列名称
             * @param durable 队列是否持久化
             * @param exclusive 是否排他，即是否为私有的，如果为true,会对当前队列加锁，其它通道不能访问，
             *                  并且在连接关闭时会自动删除，不受持久化和自动删除的属性控制。
             *                  一般在队列和交换器绑定时使用
             * @param autoDelete 是否自动删除，当最后一个消费者断开连接之后是否自动删除
             * @param arguments 队列参数，设置队列的有效期、消息最大长度、队列中所有消息的生命周期等等
             */
            channel.queueDeclare("queue1", true, false, false, null);

            // 7、定义收到消息后的回调
            final Channel finalChannel = channel;
            DeliverCallback callback = new DeliverCallback() {
                public void handle(String consumerTag, Delivery message) throws IOException {
                    System.out.println("收到消息：" + new String(message.getBody(), "UTF-8"));
                    finalChannel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                }
            };
            // 8、监听队列
            channel.basicConsume("queue1", false, callback, new CancelCallback() {
                public void handle(String consumerTag) throws IOException {
                }
            });

            System.out.println("开始接收消息");
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 9、关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
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

package com.study.mquse.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: allen
 * @Date: 2019/2/26 19:58
 */
public class Recv {
    private final static String QUEUE_NAME="hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //下面的配置与生产者相对应
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);//服务器ip
        factory.setPort(RabbitConfig.PORT);//端口
        factory.setUsername(RabbitConfig.USERNAME);//登录名
        factory.setPassword(RabbitConfig.PASSWORD);//密码
        Connection connection=factory.newConnection();//连接
        Channel channel=connection.createChannel();//频道
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);//队列
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //defaultConsumer实现了Consumer，我们将使用它来缓存生产者发送过来储存在队列中的消息。当我们可以接收消息的时候，从中获取。
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        //接收到消息以后，推送给RabbitMQ，确认收到了消息。
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

}
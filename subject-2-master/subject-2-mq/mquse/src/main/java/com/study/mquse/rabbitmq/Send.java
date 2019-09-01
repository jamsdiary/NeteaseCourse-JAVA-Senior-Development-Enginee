package com.study.mquse.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: allen
 * @Date: 2019/2/26 19:56
 */
public class Send {
    private final static String QUEUE_NAME="f";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);//服务器ip
        factory.setPort(RabbitConfig.PORT);//端口
        factory.setUsername(RabbitConfig.USERNAME);//登录名
        factory.setPassword(RabbitConfig.PASSWORD);//密码
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message="hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent '"+message+"'");
        channel.close();
        connection.close();
    }

}
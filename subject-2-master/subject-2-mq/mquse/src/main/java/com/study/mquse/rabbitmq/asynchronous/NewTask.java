package com.study.mquse.rabbitmq.asynchronous;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.study.mquse.rabbitmq.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 编写一个生产者，它除了执行将注册数据存储进数据库的方法外，
 * 还向RabbitMQ队列里发送了两条消息，分别用于存储有关邮箱验证和日志存储的内容。
 *
 * @Auther: allen
 * @Date: 2019/2/27 10:52
 */
public class NewTask {
    private final static String QUEUE_NAME = "register";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);//服务器ip
        factory.setPort(RabbitConfig.PORT);//端口
        factory.setUsername(RabbitConfig.USERNAME);//登录名
        factory.setPassword(RabbitConfig.PASSWORD);//密码
        Connection connection = factory.newConnection();//建立连接
        Channel channel = connection.createChannel();//建立频道
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);//建立一个队列
/* setp1

        System.out.println("首先，保存用户注册数据到数据库");

        JSONObject jsonObjet1 = new JSONObject();
        jsonObjet1.put("msgType", "email");//该消息是针对发送验证邮件的。
        jsonObjet1.put("content", "执行发送验证邮件到邮箱操作");
        String message1 = jsonObjet1.toString();
        channel.basicPublish("", QUEUE_NAME, null, message1.getBytes());//发布第一个异步消息
        System.out.println(channel+" Sent '"+message1+"'");

        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("msgType", "log");//该消息针对存储操作日志
        jsonObject2.put("content", "执行存储操作日志的操作");
        String message2=jsonObject2.toString();
        channel.basicPublish("", QUEUE_NAME, null, message2.getBytes());//发布第二个异步消息
        System.out.println(channel+" Sent '"+message2+"'");
        channel.close();
        connection.close();
    }
 */

/* setp2
 * 稍微改动一下NewTask方法，让它一次性发送多条消息到队列中
     */
        for (int i = 0; i < 10; i++) {
            JSONObject jsonObjet1 = new JSONObject();
            jsonObjet1.put("msgType", "email");//该消息是针对发送验证邮件的。
            jsonObjet1.put("content", "执行发送验证邮件到邮箱操作" + i);
            String message1 = jsonObjet1.toString();
            channel.basicPublish("", QUEUE_NAME, null, message1.getBytes());//发布第一个异步消息
            System.out.println(channel + " Sent '" + message1 + "'");
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("msgType", "log");//该消息针对存储操作日志
            jsonObject2.put("content", "执行存储操作日志的操作" + i);
            String message2 = jsonObject2.toString();
            channel.basicPublish("", QUEUE_NAME, null, message2.getBytes());
            System.out.println(channel + " Sent '" + message2 + "'");
        }
        channel.close();
        connection.close();
    }

}

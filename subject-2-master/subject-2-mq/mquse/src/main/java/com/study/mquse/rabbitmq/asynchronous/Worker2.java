package com.study.mquse.rabbitmq.asynchronous;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import com.study.mquse.rabbitmq.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 编写一个消费者Worker2，用于分担Worker1的负担，它的代码与worker1基本类似，
 * 我们修改了worker1和worker2的代码，加入睡眠机制，每一个worker执行完消息的任务以后
 *
 * @Auther: allen
 * @Date: 2019/2/27 14:35
 */
public class Worker2 {
    private final static String QUEUE_NAME = "register";

    public static void main(String[] args) throws IOException, TimeoutException {
        //下面的配置与生产者相对应
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);//服务器ip
        factory.setPort(RabbitConfig.PORT);//端口
        factory.setUsername(RabbitConfig.USERNAME);//登录名
        factory.setPassword(RabbitConfig.PASSWORD);//密码
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //每次从队列获取的数量
        channel.basicQos(1);
        //defaultConsumer实现了Consumer，我们将使用它来缓存生产者发送过来储存在队列中的消息。当我们可以接收消息的时候，从中获取。
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    JSONObject jsonObject = JSON.parseObject(message);
                    // JSONObject jsonObject = JSONObject.fromObject(message);
                    String msgType = jsonObject.get("msgType").toString();
                    if (msgType.equals("email")) {
                        //调用邮箱验证代码
                        System.out.println("worker2 do " + jsonObject.get("content"));
                    } else {
                        //调用日志保存代码
                        System.out.println("worker2 do " + jsonObject.get("content"));
                    }
                } catch (Exception e) {
                    channel.abort();
                } finally {
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    //执行以后睡一会，好让其他的worker有机会执行任务
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        //接收到消息以后，推送给RabbitMQ，告诉他确认收到了消息。第二个参数为false，表示手动确认消息处理完毕
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}

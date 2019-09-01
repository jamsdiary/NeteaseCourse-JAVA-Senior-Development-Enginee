package com.study.mquse.rabbitmq.asynchronous;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.*;
import com.study.mquse.rabbitmq.RabbitConfig;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 编写一个消费者worker1，在worker1中，
 * 根据接收到的消息类型，调用不同的处理方法来处理消息中的任务。
 *
 * @Auther: allen
 * @Date: 2019/2/27 14:26
 */
public class Worker1 {
    private final static String QUEUE_NAME="register";

    public static void main(String[] args) throws IOException, TimeoutException {
        //下面的配置与生产者相对应
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost(RabbitConfig.HOST);//服务器ip
        factory.setPort(RabbitConfig.PORT);//端口
        factory.setUsername(RabbitConfig.USERNAME);//登录名
        factory.setPassword(RabbitConfig.PASSWORD);//密码
        Connection connection=factory.newConnection();
        final Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" worker1 Waiting for messages. To exit press CTRL+C");
        //每次从队列获取的数量
        channel.basicQos(1);
        //defaultConsumer实现了Consumer，我们将使用它来缓存生产者发送过来储存在队列中的消息。当我们可以接收消息的时候，从中获取。
        Consumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    JSONObject jsonObject = JSON.parseObject(message);
                    // JSONObject jsonObject = JSONObject.fromObject(message);
                    String msgType=jsonObject.get("msgType").toString();
                    System.out.println(" wokrer1 Received message,msgType is " + msgType);
                    if(msgType.equals("email")){
                        //调用邮箱验证代码
                        System.out.println("worker1 do "+jsonObject.get("content"));
                    }else{
                        //调用日志保存代码
                        System.out.println("worker1 do "+jsonObject.get("content"));
                    }
                } catch (Exception e) {
                    channel.abort();
                }finally{
                    System.out.println("Worker1 Done");
                    //注意这句为必须，否则会造成RabbitMQ因为重复的重新发送已处理的消息而内存溢出
                    channel.basicAck(envelope.getDeliveryTag(),false);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        //接收到消息以后，推送给RabbitMQ，确认收到了消息。第二个参数为false，表示手动确认消息处理完毕
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
package com.study.rocketmq.a156_batch;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量消息生产者
 */
public class BatchMessageProducer {
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, UnsupportedEncodingException {
        // 1. 创建生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        producer.setNamesrvAddr("192.168.100.242:9876");

        // 3. 启动生产者
        producer.start();

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            String content = "Hello batch message " + i;
            Message message = new Message("TopicTest", content.getBytes(RemotingHelper.DEFAULT_CHARSET));

            messages.add(message);
        }
        // 5. 发送消息
        SendResult result = producer.send(messages);
        System.out.println("消息已发送：" + result);

        // 6. 停止生产者
        producer.shutdown();
    }
}

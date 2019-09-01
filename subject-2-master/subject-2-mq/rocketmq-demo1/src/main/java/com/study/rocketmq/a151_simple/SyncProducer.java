package com.study.rocketmq.a151_simple;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * 发送同步消息
 * 可靠的同步传输用于广泛的场景，如重要的通知消息，短信通知，短信营销系统等。
 */
public class SyncProducer {
    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        // 1. 创建生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        producer.setNamesrvAddr(NAME_SERVER_ADDR);

        // 3. 启动生产者
        producer.start();

        // 4. 生产者发送消息
        for (int i = 0; i < 10; i++) {
            Message message = new Message("TopicTest", "TagA", ("Hello MQ:" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult result = producer.send(message);

            System.out.printf("发送结果：%s%n", result);
        }

        // 5. 停止生产者
        producer.shutdown();
    }
}
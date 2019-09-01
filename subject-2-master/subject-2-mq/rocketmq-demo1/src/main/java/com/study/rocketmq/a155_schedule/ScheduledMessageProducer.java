package com.study.rocketmq.a155_schedule;

import org.apache.commons.lang3.RandomUtils;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 定时消息生产者
 */
public class ScheduledMessageProducer {

    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, UnsupportedEncodingException {
        // 1. 创建生产者对象
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        producer.setNamesrvAddr(NAME_SERVER_ADDR);

        // 3. 启动生产者
        producer.start();

        for (int i = 0; i < 10; i++) {
            String content = "Hello scheduled message " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(new Date());
            Message message = new Message("TopicTest", content.getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 4. 设置延时等级，此消息将在10秒后传递给消费者
            // 可以在broker服务器端自行配置messageDelayLevel=1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(3);

            // 5. 发送消息
            SendResult result = producer.send(message);

            System.out.printf("发送结果：%s%n", result);
            TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(300, 800));
        }

        // 6. 停止生产者
        producer.shutdown();
    }
}

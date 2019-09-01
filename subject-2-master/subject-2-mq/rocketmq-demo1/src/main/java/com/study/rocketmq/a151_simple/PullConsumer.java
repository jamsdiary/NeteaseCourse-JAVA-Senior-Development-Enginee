package com.study.rocketmq.a151_simple;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 普通消息消费者
 */
public class PullConsumer {

    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";

    public static void main(String[] args) throws Exception {
        // 1. 创建消费者（Pull）对象
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.start();
        // 3. 获取到对于topic的queue列表
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                // 4. 循环遍历
                for (MessageQueue messageQueue : messageQueues) {
                    // 5. 获取读取位置
                    long offset = consumer.fetchConsumeOffset(messageQueue, true);
                    // 6. 从指定位置取queue中的消息，每次最多10条。  如果没有则阻塞等待
                    PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, null, offset, 10);
                    // 7. 存储Offset，客户端每隔5s会定时刷新到Broker()
                    System.out.println(pullResult.getNextBeginOffset());
                    consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                    // 8. 遍历结果
                    if (pullResult.getPullStatus() == PullStatus.FOUND) {
                        List<MessageExt> messageExtList = pullResult.getMsgFoundList();
                        for (MessageExt messageExt : messageExtList) {
                            System.out.printf("线程：%-25s 接收到新消息 %s --- %s %n", Thread.currentThread().getName(), messageExt.getTags(), new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1000L, 1000L, TimeUnit.MILLISECONDS);
    }
}

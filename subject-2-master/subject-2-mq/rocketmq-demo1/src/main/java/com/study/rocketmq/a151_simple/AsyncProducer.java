package com.study.rocketmq.a151_simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * 异步消息
 * 一般用来对方法调用响应时间有较严格要求的情况下，异步调用，立即返回
 * 不同于同步的唯一在于： send方法调用的时候多携带一个回调接口参数，用来异步处理消息发送结果
 */
public class AsyncProducer {
    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
        // 1：创建生产者对象，并指定组名
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_TEST");

        // 2：指定NameServer地址
        producer.setNamesrvAddr(NAME_SERVER_ADDR);

        // 3：启动生产者
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0); // 设置异步发送失败重试次数，默认为2

        int count = 10;
        CountDownLatch cd = new CountDownLatch(count);
        // 4：循环发送消息
        for (int i = 0; i < count; i++) {
            final int index = i;

            // ID110：业务数据的ID，比如用户ID、订单编号等等
            Message msg = new Message("TopicTest", "TagB", "ID110", ("Hello World " + index).getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送异步消息
            producer.send(msg, new SendCallback() {
                /**
                 * 发送成功的回调函数
                 * 但会结果有多种状态，在SendStatus枚举中定义
                 * @param sendResult
                 */
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK MSG_ID:%s %n", index, sendResult.getMsgId());
                    cd.countDown();
                }

                /**
                 * 发送失败的回调函数
                 * @param e
                 */
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                    cd.countDown();
                }
            });
        }

        // 确保消息都发送出去了
        cd.await();
        // 5：关闭生产者
        producer.shutdown();
    }
}

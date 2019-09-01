package com.study.rocketmq.a153_order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 顺序消息消费者
 */
public class OrderedConsumer {
    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";
    public static void main(String[] args) throws Exception {
        // 1. 创建消费者（Push）对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 3. 订阅对应的主题和Tag
        consumer.subscribe("TopicTest", "TagA || TagB || TagC");

        // 4. 注册消息接收到Broker消息后的处理接口
        // 注1：普通消息消费 [[
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            AtomicInteger count = new AtomicInteger(0);
//
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                doBiz(list.get(0));
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
        // ]] 注1：普通消息消费

        // consumer
        consumer.setMaxReconsumeTimes(-1);
        // 延时  level  3

        // 注2：顺序消息消费 [[
        consumer.registerMessageListener(new MessageListenerOrderly() {

            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs,
                                                       ConsumeOrderlyContext context) {
                context.setAutoCommit(true);
                doBiz(msgs.get(0));

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // ]] 注2：顺序消息消费

        // 5. 启动消费者(必须在注册完消息监听器后启动，否则会报错)
        consumer.start();

        System.out.println("已启动消费者");
    }

    /**
     * 模拟处理业务
     *
     * @param message
     */
    public static void doBiz(Message message) {
        try {
            System.out.printf("线程：%-25s 接收到新消息 %s --- %s %n", Thread.currentThread().getName(), message.getTags(), new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}

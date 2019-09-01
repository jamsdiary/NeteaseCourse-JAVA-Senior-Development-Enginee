package com.study.rocketmq.a153_order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有序消息
 */
public class OrderedProducer {
    public static final String NAME_SERVER_ADDR = "192.168.100.242:9876";

    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException, UnsupportedEncodingException {
        // 1：创建生产者对象，并指定组名
        DefaultMQProducer producer = new DefaultMQProducer("GROUP_TEST");

        // 2：指定NameServer地址
        producer.setNamesrvAddr(NAME_SERVER_ADDR);

        // 3：启动生产者
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0); // 设置异步发送失败重试次数，默认为2

        // 4：定义消息队列选择器
        MessageQueueSelector messageQueueSelector = new MessageQueueSelector() {

            /**
             * 消息队列选择器，保证同一条业务数据的消息在同一个队列
             * @param mqs topic中所有队列的集合
             * @param msg 发送的消息
             * @param arg 此参数是本示例中producer.send的第三个参数
             * @return
             */
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Integer id = (Integer) arg;
                // id == 1001
                int index = id % mqs.size();
                // 分区顺序：同一个模值的消息在同一个队列中
                return mqs.get(index);

                // 全局顺序：所有的消息都在同一个队列中
                // return mqs.get(mqs.size() - 1);
            }
        };

        String[] tags = new String[]{"TagA", "TagB", "TagC"};

        List<Map> bizDatas = getBizDatas();

        // 5：循环发送消息
        for (int i = 0; i < bizDatas.size(); i++) {
            Map bizData = bizDatas.get(i);
            // keys：业务数据的ID，比如用户ID、订单编号等等
            Message msg = new Message("TopicTest", tags[i % tags.length], "" + bizData.get("msgType"), bizData.toString().getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 发送有序消息
            SendResult sendResult = producer.send(msg, messageQueueSelector, bizData.get("msgType"));

            System.out.printf("%s， body:%s%n", sendResult, bizData);
        }

        // 6：关闭生产者
        producer.shutdown();
    }

    public static List<Map> getBizDatas() {
        List<Map> orders = new ArrayList<Map>();

        HashMap orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "存钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "取钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "存钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "存钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "存钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "取钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "取钱1000");
        orders.add(orderData);


        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "取钱1000");
        orders.add(orderData);

        orderData = new HashMap();
        orderData.put("msgType", 1001);
        orderData.put("userId", "tony");
        orderData.put("desc", "存钱1000");
        orders.add(orderData);

        return orders;
    }
}

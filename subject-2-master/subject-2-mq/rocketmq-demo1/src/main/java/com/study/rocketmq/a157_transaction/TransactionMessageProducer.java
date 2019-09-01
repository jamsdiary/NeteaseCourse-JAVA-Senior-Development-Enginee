package com.study.rocketmq.a157_transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.IOException;

/**
 * 事务消息生产者
 */
public class TransactionMessageProducer {
    /**
     * 事务消息监听实现
     */
    private final static TransactionListener transactionListenerImpl = new TransactionListener() {

        /**
         * 在发送消息成功时执行本地事务
         * @param msg
         * @param arg producer.sendMessageInTransaction的第二个参数
         * @return 返回事务状态
         * LocalTransactionState.COMMIT_MESSAGE：提交事务，提交后broker才允许消费者使用
         * LocalTransactionState.RollbackTransaction：回滚事务，回滚后消息将被删除，并且不允许别消费
         * LocalTransactionState.Unknown：中间状态，表示MQ需要核对，以确定状态
         */
        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            // TODO 开启本地事务（实际就是我们的jdbc操作）

            // TODO 执行业务代码（插入订单数据库表）
            // int i = orderDatabaseService.insert(....)
            // TODO 提交或回滚本地事务(如果用spring事务注解，这些都不需要我们手工去操作)

            // 模拟一个处理结果
            int index = 8;
            /**
             * 模拟返回事务状态
             */
            switch (index) {
                case 3:
                    System.out.printf("本地事务回滚，回滚消息，id:%s%n", msg.getKeys());
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                case 5:
                case 8:
                    return LocalTransactionState.UNKNOW;
                default:
                    System.out.println("事务提交，消息正常处理");
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }

        /**
         * Broker端对未确定状态的消息发起回查，将消息发送到对应的Producer端（同一个Group的Producer），
         * 由Producer根据消息来检查本地事务的状态，进而执行Commit或者Rollback
         * @param msg
         * @return 返回事务状态
         */
        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            // 根据业务，正确处理： 订单场景，只要数据库有了这条记录，消息应该被commit
            String transactionId = msg.getTransactionId();
            String key = msg.getKeys();
            System.out.printf("回查事务状态 key:%-5s msgId:%-10s transactionId:%-10s %n", key, msg.getMsgId(), transactionId);

            if ("id_5".equals(key)) { // 刚刚测试的10条消息， 把id_5这条消息提交，其他的全部回滚。
                System.out.printf("回查到本地事务已提交，提交消息，id:%s%n", msg.getKeys());
                return LocalTransactionState.COMMIT_MESSAGE;
            } else {
                System.out.printf("未查到本地事务状态，回滚消息，id:%s%n", msg.getKeys());
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }
    };

    public static void main(String[] args) throws MQClientException, IOException {
        // 1. 创建事务生产者对象
        // 和普通消息生产者有所区别，这里使用的是TransactionMQProducer
        TransactionMQProducer producer = new TransactionMQProducer("GROUP_TEST");

        // 2. 设置NameServer的地址，如果设置了环境变量NAMESRV_ADDR，可以省略此步
        producer.setNamesrvAddr("192.168.100.242:9876");

        // 3. 设置事务监听器
        producer.setTransactionListener(transactionListenerImpl);

        // 4. 启动生产者
        producer.start();

        for (int i = 0; i < 10; i++) {
            String content = "Hello transaction message " + i;
            Message message = new Message("TopicTest", "TagA", "id_" + i, content.getBytes(RemotingHelper.DEFAULT_CHARSET));

            // 5. 发送消息(发送一条新订单生成的通知)
            SendResult result = producer.sendMessageInTransaction(message, i);

            System.out.printf("发送结果：%s%n", result);
        }

        System.in.read();
        // 6. 停止生产者
        producer.shutdown();
    }
}

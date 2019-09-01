package com.study.cache.redis.a4_stream;

import io.lettuce.core.Consumer;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("stream") // 设置profile
public class StreamTests {
    // stream 流，5.0新特性，redisTemplate、jedis还没有支持,Redisson和Lettuce支持了
    // 我们使用springboot中默认的redis客户端Lettuce
    // 添加： XADD mystream * sensor-id 1234 temperature 19.8
    // 遍历： XRANGE mystream - + COUNT 2
    // 消费：XREAD COUNT 2 STREAMS mystream 0
    // 阻塞式消费： XREAD BLOCK 0 STREAMS mystream $
    // 创建消费者组：   XGROUP CREATE mystream mygroup $
    // 分组消费： XREADGROUP GROUP mygroup Alice COUNT 1 STREAMS mystream >
    // 消费确认： XACK mystream mygroup 1526569495631-0
    // 查看未确认的消息： XPENDING mystream mygroup - + 10
    // 重新认领消费：XCLAIM mystream mygroup Alice 3600000 1526569498055-0
    // XINFO 查看stream信息，监控
    @Test
    public void producer() throws InterruptedException {
        RedisClient redisClient = RedisClient.create("redis://192.168.100.241:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> redisSyncCommands = connect.sync();
        redisSyncCommands.xadd("stream_sms_send", "smsid", "10001", "content", "收到短信请回复");
    }

    // 普通消费 -- 最后一条消息
    @Test
    public void consumer() {
        RedisClient redisClient = RedisClient.create("redis://192.168.100.241:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> redisSyncCommands = connect.sync();
        List<StreamMessage<String, String>> stream_sms_send = redisSyncCommands.xread(XReadArgs.StreamOffset.from("stream_sms_send", "0"));
        for (StreamMessage<String, String> stringStringStreamMessage : stream_sms_send) {
            System.out.println(stringStringStreamMessage);
        }
    }

    @Test
    public void createGroup() {
        RedisClient redisClient = RedisClient.create("redis://192.168.100.241:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> redisSyncCommands = connect.sync();
        // 创建分组
        redisSyncCommands.xgroupCreate(XReadArgs.StreamOffset.from("stream_sms_send", "0"), "group_1");
    }

    @Test
    public void consumerGroup() {
        RedisClient redisClient = RedisClient.create("redis://192.168.100.241:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> redisSyncCommands = connect.sync();
        // 按组消费
        List<StreamMessage<String, String>> xreadgroup = redisSyncCommands.xreadgroup(Consumer.from("group_1", "consumer_1"), XReadArgs.StreamOffset.lastConsumed("stream_sms_send"));
        for (StreamMessage<String, String> message : xreadgroup) {
            System.out.println(message);
            // 告知redis，消息已经完成了消费
            redisSyncCommands.xack("stream_sms_send", "group_1", message.getId());
        }
    }

    @Test
    public void consumerAck() {

    }
}

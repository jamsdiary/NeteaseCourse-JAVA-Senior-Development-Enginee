package com.study.cache.redis.a3_pubsub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 接收短信通知，直接用客户端的方式
 */
@Component
@Profile("pubsub")
public class SmsChannelListener {
    @Autowired
    RedisTemplate redisTemplate;

    @PostConstruct
    public void setup() {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.subscribe((message, pattern) -> {
                    System.out.println("收到消息，使用redisTemplate收到的：" + message);
                }, PubsubRedisAppConfig.TEST_CHANNEL_NAME.getBytes());
                return null;
            }
        });
    }
}

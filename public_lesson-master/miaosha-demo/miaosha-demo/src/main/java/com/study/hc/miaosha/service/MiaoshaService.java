package com.study.hc.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class MiaoshaService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DatabaseService databaseService;


    @PostConstruct
    public void init() {
        // 初始化 令牌(启动的时候，初始化150个令牌到 redis中存储)
        for (int i = 0; i < 150; i++) {
            stringRedisTemplate.opsForList().leftPush("token_list", "token_content_" + i);
        }
        // 初始化。
        System.out.println("初始化完毕");
    }

    /**
     * 秒杀具体实现
     *
     * @param goodsCode 商品编码
     * @param userId    用户ID
     * @return
     */
    public boolean miaosha(String goodsCode, final String userId) {
        // 限制同一个用户的操作。10秒操作一次  // setnx  setex  不能分开。  set nx ex  // redis性能 10W/s  ---> 5W/s
        Boolean success = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.set(userId.getBytes(), "xxoo".getBytes(), Expiration.milliseconds(10000), RedisStringCommands.SetOption.SET_IF_ABSENT.SET_IF_ABSENT);
            }
        });
        if (!success) {
            System.out.println("你的频率太快了...：" + userId);
            return false;
        }

        // 减轻数据库的压力 2C/4G
        String token = stringRedisTemplate.opsForList().leftPop("token_list");
        if (token == null || "".equals(token)) {
            System.out.println("没抢到token，拜拜：" + userId);
            return false;
        }

        boolean result = databaseService.buy(goodsCode, userId);
        System.out.println("秒杀结果:" + result);
        return result;
    }
}

package com.study.cache.redis.a1_pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("pipeline") // 设置profile
public class PipelineTests {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void test1() throws InterruptedException {
        // 普通模式和pipeline模式
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForList().leftPush("queue_1", i);
        }
        System.out.println("操作完毕：" + redisTemplate.opsForList().size("queue_1"));
        System.out.println("普通模式一万次操作耗时：" + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    connection.lPush("queue_2".getBytes(), String.valueOf(i).getBytes());
                }
                return null;
            }
        });
        System.out.println("操作完毕：" + redisTemplate.opsForList().size("queue_2"));
        System.out.println("pipeline一万次操作耗时：" + (System.currentTimeMillis() - time));


    }
}

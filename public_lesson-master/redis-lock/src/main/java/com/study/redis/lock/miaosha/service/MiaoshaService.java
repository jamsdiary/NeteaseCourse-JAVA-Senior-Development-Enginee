package com.study.redis.lock.miaosha.service;

import com.study.redis.lock.miaosha.util.RedisLockImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MiaoshaService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DatabaseService databaseService;

    Lock lock = null;

    @PostConstruct
    public void init() {
        lock = new RedisLockImpl(stringRedisTemplate, "goods_code_bike", 10);
    }

    /**
     * 秒杀具体实现
     *
     * @param goodsCode 商品编码
     * @param userId    用户ID
     * @return
     */
    public boolean miaosha(String goodsCode, final String userId) {
        lock.lock(); // synchronized{}
        boolean result = false;
        try {
            result = databaseService.buy(goodsCode, userId);
            System.out.println("秒杀结果:" + result);
            if (result) {
                // 更新库存
                // 示例：如果秒杀成功，更新一次库存(库存一般是在缓存里面，供页面快速查询)
                String count = databaseService.getCount(goodsCode);
                // 模拟线程运行延时，随机睡眠
                if (Integer.valueOf(count) % 2 == 1) Thread.sleep(new Random().nextInt(500));
                stringRedisTemplate.opsForValue().set(goodsCode, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }
}

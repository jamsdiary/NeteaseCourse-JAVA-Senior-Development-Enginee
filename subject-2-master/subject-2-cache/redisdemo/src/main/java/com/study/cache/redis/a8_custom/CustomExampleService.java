package com.study.cache.redis.a8_custom;

import com.study.cache.redis.a8_custom.annotations.Cache;
import com.study.cache.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("custom")
public class CustomExampleService {
    // 直接注入StringRedisTemplate，则代表每一个操作参数都是字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 参数可以是任何对象，默认由JDK序列化
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 简单的缓存插入功能
     */
    public void setByCache(String userId, String userInfo) {
        stringRedisTemplate.opsForValue().set(userId, userInfo);
    }

    /**
     * 对象缓存功能
     */
    public User findUser(String userId) throws Exception {
        // 首先加载用户信息， 查询之前，先判定请求是否合法。 预习（）

        User user = null;
        // 1、 判定缓存中是否存在
        user = (User) redisTemplate.opsForValue().get(userId);

        if (user != null) {
            System.out.println("从缓存中读取到值：" + user);
            return user;
        }

        // 1、 返回错误码
        // 2、 不设置过期时间（异常情况下无数据，缓存服务器数据丢失，可以结合同步锁）

        // 热点数据不过期，防止了自动失效情况
        // 通过其他的后台检查程序，防止缓存数据长期和数据库不同步
        synchronized (this) { // 同步~  1个线程拿到锁，其他线程等待。 数据库压力减小~
            //  高并发怼到数据库~~ 5000 get tony
            // TODO 2、不存在则读取数据库或者其他地方的值
            user = new User(userId, "张三");
            System.out.println("从数据库中读取到值：" + user);
            // 3、 同步存储value到缓存。
            redisTemplate.opsForValue().set(userId, user);
        }
        return user;
    }

    @Cache(key = "#userId")
    public User findUserById(String userId) throws Exception {
        User user = null;
        user = new User(userId, "张三");
        System.out.println("从数据库中读取到值：" + user);
        return user;
    }
}
package com.study.security.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RedissLockConfig
 * @Description Rediss 实现分布式锁
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/7/14 20:11
 * @Version 1.0
 */
@Configuration
public class RedissLockConfig {

    /***
     * @Description 装配 RLock
     * @Author 网易云课堂微专业 - java高级开发工程
     * @Date 2019/7/14 20:19
     * @Param [lockName] 锁名词
     * @return org.redisson.api.RLock
     **/
    @Bean
    public RLock getRedissonLock(String lockName) {
        Config config = new Config();
        // use "rediss://" for SSL connection
        // config.useClusterServers().addNodeAddress("redis://192.168.100.100:6379");
        config.useSingleServer().setAddress("redis://192.168.100.241:6379");
        Redisson redisson = (Redisson) Redisson.create(config);
        // 获得锁对象实例
        RLock lock = redisson.getLock(lockName);
        return lock;
    }

}

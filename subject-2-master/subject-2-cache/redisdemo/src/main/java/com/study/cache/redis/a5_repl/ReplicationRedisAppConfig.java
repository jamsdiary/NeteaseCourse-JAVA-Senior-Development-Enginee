package com.study.cache.redis.a5_repl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("replication") // 主从模式
class ReplicationRedisAppConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        // master:192.168.100.241    slave:192.168.100.242
        // 默认slave只能进行读取，不能写入
        // 如果你的应用程序需要往redis写数据，建议连接master
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("192.168.100.241", 6379));
    }
}
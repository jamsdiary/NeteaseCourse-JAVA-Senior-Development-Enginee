package com.study.cache.redis.a7_cluster;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Arrays;

@Configuration
// 在cluster环境下生效
@Profile("a7_cluster")
class ClusterAppConfig {
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        System.out.println("加载cluster环境下的redis client配置");
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(Arrays.asList(
                "192.168.100.242:6381",
                "192.168.100.242:6382",
                "192.168.100.242:6383",
                "192.168.100.242:6384",
                "192.168.100.242:6385",
                "192.168.100.242:6386"
        ));
        // 自适应集群变化
        return new JedisConnectionFactory(redisClusterConfiguration);
    }
}
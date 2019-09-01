package com.study.cache.redis.a1_cluster;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("cluster")
public class ClusterAppConfig {
    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        String servers = "192.168.100.243:11211 192.168.100.243:11212 192.168.100.243:11213";
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil
                .getAddresses(servers));
        // 默认的客户端计算就是 key的哈希值模以连接数
        // KetamaMemcachedSessionLocator 一致性hash算法
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        MemcachedClient client = builder.build();
        return client;
    }
}

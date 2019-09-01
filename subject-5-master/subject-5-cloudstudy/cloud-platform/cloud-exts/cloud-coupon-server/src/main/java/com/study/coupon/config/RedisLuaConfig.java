package com.study.coupon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

// 加载抢优惠券需要用到的lua脚本
@Configuration
public class RedisLuaConfig {
    @Bean
    public DefaultRedisScript<Long> couponAcquireRedisScript() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis-lua-scripts/coupon-acquire.lua")));
        return defaultRedisScript;
    }
}

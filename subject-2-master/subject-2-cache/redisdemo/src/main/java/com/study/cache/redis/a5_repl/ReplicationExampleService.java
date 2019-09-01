package com.study.cache.redis.a5_repl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReplicationExampleService {
    @Autowired
    private StringRedisTemplate template;

    public void setByCache(String userId, String userInfo) {
        template.opsForValue().set(userId, userInfo);
    }

    public String getByCache(String userId) {
        return template.opsForValue().get(userId);
    }
}
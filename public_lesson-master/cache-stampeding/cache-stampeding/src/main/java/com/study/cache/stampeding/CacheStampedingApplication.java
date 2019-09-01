package com.study.cache.stampeding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class CacheStampedingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheStampedingApplication.class, args);
	}
}

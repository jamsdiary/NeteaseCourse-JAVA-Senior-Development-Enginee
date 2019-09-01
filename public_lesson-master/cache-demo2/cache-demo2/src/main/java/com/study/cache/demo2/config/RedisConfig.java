package com.study.cache.demo2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 多Redis配置
 */
@Configuration
public class RedisConfig {
	// ---- Redis ---- 主缓存
	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.redis.main")
	public RedisStandaloneConfiguration mainRedisConfig() {
		return new RedisStandaloneConfiguration();
	}

	@Bean
	@Primary
	public LettuceConnectionFactory mainRedisConnectionFactory(RedisStandaloneConfiguration mainRedisConfig) {
		return new LettuceConnectionFactory(mainRedisConfig);
	}

	@Bean
	public RedisTemplate<String, String> mainRedisTemplate(LettuceConnectionFactory mainRedisConnectionFactory) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(mainRedisConnectionFactory);
		return redisTemplate;
	}

	// ---- Redis ---- 备份缓存
	@Bean
	@ConfigurationProperties(prefix = "spring.redis.bak")
	public RedisStandaloneConfiguration bakRedisConfig() {
		return new RedisStandaloneConfiguration();
	}

	@Bean
	public LettuceConnectionFactory baknRedisConnectionFactory(RedisStandaloneConfiguration bakRedisConfig) {
		return new LettuceConnectionFactory(bakRedisConfig);
	}

	@Bean
	public RedisTemplate<String, String> bakRedisTemplate(LettuceConnectionFactory baknRedisConnectionFactory) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(baknRedisConnectionFactory);
		return redisTemplate;
	}
}

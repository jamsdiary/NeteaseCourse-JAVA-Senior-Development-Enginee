package com.study.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis 工具类
 * 
 * @author allen
 *
 */
@Component
public class RedisUtils {
	@Autowired
	private JedisPool jedisPool;

	public Long getIncr(String key, int timeout) {
		Jedis redis = null;
		try {
			redis = jedisPool.getResource();
			/**
			 * incr(key)是redis的一个同步方法，用于对key自增加1；当key不存在时，则创建值为0的key。
			 **/
			long id = redis.incr(key);
			if (timeout > 0) {
				redis.expire(key, timeout); // 设置超时 很重要！很重要！很重要
			}
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (redis != null) {
				redis.close();
			}
		}
		return null;
	}
}

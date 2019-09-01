package com.study.cache.stampeding.service;

import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 改进版本:Lock版本
 * 
 */
@Component
public class TicketService3 {

	private final Logger logger = LoggerFactory.getLogger(TicketService3.class);

	@Resource(name = "mainRedisTemplate")
	RedisTemplate<String, String> mainRedisTemplate;

	@Autowired
	DatabaseService databaseService;

	ReentrantLock lock = new ReentrantLock();

	/**
	 * 查询车次余票
	 * 
	 * @param ticketSeq 车次
	 * @return 余票数量
	 */
	public Object queryTicketStock(String ticketSeq) {
		// 1. 先从redis中获取余票信息
		String value = mainRedisTemplate.opsForValue().get(ticketSeq);
		if (value != null) {
			logger.warn(Thread.currentThread().getName() + "缓存中取得数据==============>" + value);
			return value;
		}

		// 锁，拿到锁的，重建缓存
		lock.lock();
		try {
			// 再查一遍数据库
			value = mainRedisTemplate.opsForValue().get(ticketSeq);
			if (value != null) {
				logger.warn(Thread.currentThread().getName() + "缓存中取得数据==============>" + value);
				return value;
			}

			// 2. 缓存中没有则取数据库
			value = databaseService.queryFromDatabase(ticketSeq);
			System.out.println(Thread.currentThread().getName() + "从数据库中取得数据==============>" + value);

			// 3. 塞到缓存 120秒过期时间，一致性
			final String v = value;
			mainRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
				return connection.setEx(ticketSeq.getBytes(), 120 * 1000, v.getBytes());
			});

		} finally {
			lock.unlock();
		}

		return value;
	}
}

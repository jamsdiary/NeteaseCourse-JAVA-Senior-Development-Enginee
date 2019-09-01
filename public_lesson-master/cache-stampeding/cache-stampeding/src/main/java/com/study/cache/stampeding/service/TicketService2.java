package com.study.cache.stampeding.service;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class TicketService2 {

	private final Logger logger = LoggerFactory.getLogger(TicketService2.class);

	@Resource(name = "mainRedisTemplate")
	RedisTemplate<String, String> mainRedisTemplate;

	@Resource(name = "bakRedisTemplate")
	RedisTemplate<String, String> bakRedisTemplate;

	@Autowired
	DatabaseService databaseService;

	// G296 -> 锁
	/** key 车次 -> value 是否有人占用了锁 */
	ConcurrentHashMap<String, String> mapLock = new ConcurrentHashMap<>();

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

		boolean lock = false;
		try {
			// 这个方法类似redis中的setnx
			lock = mapLock.putIfAbsent(ticketSeq, "true") == null;

			// 2000并发，1个拿到锁，1999干什么？
			if (lock) {
				// 拿到锁，重建缓存
				value = mainRedisTemplate.opsForValue().get(ticketSeq);
				if (value != null) {
					logger.warn(Thread.currentThread().getName() + "缓存中取得数据==============>" + value);
					return value;
				}

				// 2. 缓存中没有则取数据库
				value = databaseService.queryFromDatabase(ticketSeq);
				System.out.println(Thread.currentThread().getName() + "从数据库中取得数据==============>" + value);

				// 3. 塞到主缓存 120秒过期时间，一致性
				final String v = value;
				mainRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
					return connection.setEx(ticketSeq.getBytes(), 120 * 1000, v.getBytes());
				});

				// 双写
				// 4. 操作备份缓存,永久有效
				bakRedisTemplate.opsForValue().set(ticketSeq, value);

			} else {
				// 缓存降级实现：根据业务场景，降低期望值
				// 第二种：备份缓存
				value = bakRedisTemplate.opsForValue().get(ticketSeq);
				if (value != null) {
					System.out.println(Thread.currentThread().getName() + "缓存降级，查询备份缓存，取得数据==============>" + value);
				} else {
					value = "0"; // 第一种降级1、返回固定值，异常值,兜底方案
					System.out.println(Thread.currentThread().getName() + "缓存降级，返回固定值，取得数据==============>" + value);
				}

			}
		} finally {
			mapLock.remove(ticketSeq);
		}

		return value;
	}
}

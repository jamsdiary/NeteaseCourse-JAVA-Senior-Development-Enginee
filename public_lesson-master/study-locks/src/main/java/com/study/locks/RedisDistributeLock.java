package com.study.locks;

import java.util.Collections;

import redis.clients.jedis.Jedis;

public class RedisDistributeLock {

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";
	private static final String SET_WITH_EXPIRE_TIME = "PX";

	/**
	 * 尝试获取分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @param expireTime
	 *            超期时间
	 * @return 是否获取成功
	 */
	public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

		if (LOCK_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}

	private static final Long RELEASE_SUCCESS = 1L;

	/**
	 * 获取锁时的睡眠等待时间片，单位毫秒
	 */
	private static final long SLEEP_PER = 5;

	public static final int DEFAULT_EXPIRE_1000_Milliseconds = 1000;

	/**
	 * 释放分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}

	public static void lock(String key, String value, int exprieTimeInMilliseconds) {
		try (Jedis jedis = JedisUtil.getJedis();) {
			while (!(tryGetDistributedLock(jedis, key, value, exprieTimeInMilliseconds))) {
				try {
					Thread.sleep(SLEEP_PER);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static boolean tryLock(String key, String value, int exprieTimeInMilliseconds) {
		try (Jedis jedis = JedisUtil.getJedis();) {
			return tryGetDistributedLock(jedis, key, value, exprieTimeInMilliseconds);
		}
	}

	public static void unlock(String key, String value) {
		try (Jedis jedis = JedisUtil.getJedis();) {
			releaseDistributedLock(jedis, key, value);
		}
	}

}

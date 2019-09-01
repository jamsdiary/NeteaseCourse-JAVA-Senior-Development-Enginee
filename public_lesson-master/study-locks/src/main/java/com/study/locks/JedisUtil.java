package com.study.locks;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private static JedisPool pool;

	private static String host = "localhost";

	private static int port = 6379;

	static {
		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();
		// 设置最大连接数
		config.setMaxTotal(10000);
		// 设置最大阻塞时间，记住是毫秒数milliseconds
		config.setMaxWaitMillis(10000);
		// 设置空间连接
		config.setMaxIdle(1000);
		pool = new JedisPool(config, host, port, 100000);
	}

	public static Jedis getJedis() {
		return pool.getResource();
	}
}

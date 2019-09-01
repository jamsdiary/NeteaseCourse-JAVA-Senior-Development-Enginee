package com.study.cache.demo2.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PriceService {

	@Resource(name = "mainRedisTemplate")
	StringRedisTemplate mainRedisTemplate;

	@Autowired
	DatabaseService databaseService;

	// 示例：JVM内存中存储
	// List<String> goodsIds = new ArrayList<>();

	@PostConstruct
	public void init() {
		// 从数据库中加载所有存在商品的ID到内存。（不断维护的，一个公共的位置存储.redis ）
//		for (int i = 0; i < 1; i++) {
//			goodsIds.add("10000001");
//		}
		
		// 初始化一个 redis的布隆过滤器
		for (int i = 0; i < 1; i++) {
			String id = "10000001";
			int hashValue = id.hashCode();
			// 计算二进制数组中的下标: 取模 余数
			long index = (long) Math.abs(hashValue % Math.pow(2, 32));
			// 设置redis中的二进制数组（长度40多亿） ，对应位置为 1
			mainRedisTemplate.opsForValue().setBit("redis_bloom_filter", index, true);
		}
	}

	/**
	 * 查询商品价格
	 * 
	 * @param goodsId 商品ID
	 * @return 价格
	 */
	public Object queryPrice(final String goodsId) {
		// 防范 击穿
		// 方案1.1 10086 xxoo 参数校验
		// 方案1.2 缓存临时空数据

		// 方案2： 加载所有的ID到内存
//		if (!goodsIds.contains(goodsId)) {
//			System.out.println("请求不合法，不存在这条记录：" + goodsId);
//			return true;
//		}

		// 方案3： 布隆过滤器的过滤(防止大量的击穿，导致数据库崩掉)
		int hashValue = goodsId.hashCode();
		// 计算二进制数组中的下标: 取模 余数
		long index = (long) Math.abs(hashValue % Math.pow(2, 32));
		Boolean result = mainRedisTemplate.opsForValue().getBit("redis_bloom_filter", index);
		if(!result) {
			System.out.println("不在数据库里面，布隆过滤器提示：" + goodsId);
			return "0";
		}

		// 1. 先从redis中获取价格信息
		String value = mainRedisTemplate.opsForValue().get(goodsId);
		if (value != null) {
			System.out.println(Thread.currentThread().getName() + "缓存中取得数据==============>" + value);
			return value;
		}
		// 2. 缓存中没有则取数据库
		value = databaseService.queryFromDatabase(goodsId);
		System.out.println(Thread.currentThread().getName() + "从数据库中取得数据==============>" + value);

		final String v = value;
		if (v != null) {
			// 3. 塞到缓存 120秒过期时间
			mainRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
				return connection.setEx(goodsId.getBytes(), 120, v.getBytes());
			});
		} else {
			// 缓存一个空值， 设置有效期。 查询不存在的数据， 一分钟内 返回0
			mainRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
				return connection.setEx(goodsId.getBytes(), 60, "0".getBytes());
			});
		}

		return value;
	}
}

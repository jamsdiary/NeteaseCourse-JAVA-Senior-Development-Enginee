package com.study.cache.nginx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotInformationController {

	@Autowired
	StringRedisTemplate stringRedisTemplate;

	/**
	 * 查询热门商品
	 * 
	 * @param type sku品类
	 * @return
	 */
	@RequestMapping("/api/query/hotInformation")
	public String queryHotInformation(String skuType) {
		// 1、 TODO 根据不同的品类查询不同的热门商品
		String result = stringRedisTemplate.opsForValue().get(skuType);
		// 2、 返回结果
		return result;
	}
}

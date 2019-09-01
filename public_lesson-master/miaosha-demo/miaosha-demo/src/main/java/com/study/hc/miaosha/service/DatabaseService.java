package com.study.hc.miaosha.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	// 记录数据库操作次数
	AtomicInteger count = new AtomicInteger(0);

	@Transactional(rollbackFor = Exception.class)
	public boolean buy(String goodsCode, String userId) {
		// 商品数量 减1
		String sql = "update tb_miaosha set goods_nums = goods_nums - 1 where goods_code = '" + goodsCode
				+ "' and goods_nums - 1 >= 0";
		int count = jdbcTemplate.update(sql);
		if (count != 1) {
			// 代表秒杀失败
			return false;
		}

		// 添加记录
		String insertSql = "INSERT INTO tb_records(goods_code, user_id) VALUES('" + goodsCode + "', '" + userId + "')";
		int insertCount = jdbcTemplate.update(insertSql);
		if (insertCount != 1) {
			return false;
		}

		return true;
	}
}

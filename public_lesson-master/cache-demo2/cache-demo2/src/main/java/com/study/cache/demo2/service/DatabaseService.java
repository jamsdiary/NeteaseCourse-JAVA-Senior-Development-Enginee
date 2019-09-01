package com.study.cache.demo2.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 从数据库中查询
 * 
 * @author tony
 *
 */
@Component
public class DatabaseService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public String queryFromDatabase(String id) {
		String sql = "SELECT goods_price FROM tb_goods_info where goods_id ='" + id + "'";

		Map<String, Object> result = jdbcTemplate.queryForMap(sql);

		return result.get("goods_price").toString();
	}
}

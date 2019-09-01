package com.study.redis.lock.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public boolean buy(String goodsCode, String userId) {
        // 商品数量 减1
        String sql = "update tb_miaosha set goods_nums = goods_nums - 1 where goods_code = '" + goodsCode
                + "' and goods_nums > 0";
        int count = jdbcTemplate.update(sql);
        if (count != 1) {
            // 代表秒杀失败
            return false;
        }
        return true;
    }

    /**
     * 获取库存数
     */
    public String getCount(String goodsCode) {
        String sql = "SELECT goods_nums FROM `tb_miaosha` where goods_code='" + goodsCode + "'";
        String count = jdbcTemplate.queryForObject(sql, String.class);
        return count;
    }
}

package com.study.coupon.schedule;

import com.study.coupon.bean.TbCoupon;
import com.study.coupon.service.CouponService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 每半小时加载商品余量信息
 */
@JobHandler(value = "loadItemScheduler")
@Component
public class LoadItemScheduler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(LoadItemScheduler.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CouponService couponService;

    @Override
    public ReturnT<String> execute(String arg0) throws Exception {
        // 循环加载,数量多那就分页加载，redis可做分片集群，如果量大
        List<TbCoupon> tbCoupons = couponService.queryCouponList();
        for (TbCoupon tbCoupon : tbCoupons) {
            // redis集群中，通过hash tag保证多个键被分配到同一个槽位的方法。{hashtag}.key
            // 当一个key包含 {} 的时候，就不对整个key做hash，而仅对 {} 包括的字符串做hash。
            // 1. 设置token。也就是redis的list类型
            String tokenKey = "{" + tbCoupon.getCouponId() + "}.tokenlists";
            stringRedisTemplate.delete(tokenKey);
            for (int i = 0; i < tbCoupon.getCouponSum(); i++) {
                // 优惠券都有对应的可抢购数值，这里的redis，是集群的
                stringRedisTemplate.opsForList().rightPush(tokenKey, String.valueOf(tbCoupon.getCouponSum()));
            }
        }
        logger.info(">>>执行加载逻辑,加载优惠券数量：{}", tbCoupons.size());
        return new ReturnT<String>("ok");
    }

}

package com.study.coupon.service;

import com.study.coupon.bean.TbCoupon;
import com.study.coupon.bean.TbCouponDetail;
import com.study.coupon.bean.TbCouponExample;
import com.study.coupon.common.CouponConstants;
import com.study.coupon.dao.TbCouponDetailMapper;
import com.study.coupon.dao.TbCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(rollbackFor = Exception.class)
public class CouponService {

    @Autowired
    TbCouponMapper tbCouponMapper;

    @Autowired
    TbCouponDetailMapper tbCouponDetailMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    DefaultRedisScript<Long> couponAcquireRedisScript;

    /**
     * 秒杀具体实现
     *
     * @param couponId 优惠券ID
     * @param userId   用户ID
     * @return
     */
    public boolean acquire(String couponId, final String userId) {
        // 0、 此用户不能重复领取（布隆过滤器）
        // 1、令牌桶策略，过滤无效用户

        Long value = stringRedisTemplate.execute(couponAcquireRedisScript,
                Arrays.asList("{" + couponId + "}.records", userId, "{" + couponId + "}.tokenlists"), "x");
        if (value == null || "".equals(value) || value == 0) {
            // 没拿到token，没有领取权限
            return false;
        }

        // TODO 具体数据库操作，可以用MQ异步处理，提示用户，稍后将发放到您的账户
        // 1. 数量减一
        int count = tbCouponMapper.acquire(couponId);
        if (count != 1) {
            return false;
        }
        // 2. 增加记录
        TbCouponDetail tbCouponDetail = new TbCouponDetail();
        tbCouponDetail.setCouponDetailId(UUID.randomUUID().toString());
        tbCouponDetail.setCouponId(couponId);
        tbCouponDetail.setUserId(Integer.valueOf(userId));
        tbCouponDetail.setCouponDetailStatus(CouponConstants.CouponDetailStatusEnum.UnUsed.getStatus()); // 0 - 未使用
        tbCouponDetailMapper.insert(tbCouponDetail);
        return true;
    }

    /**
     * 新增
     */
    public void addCoupon(TbCoupon tbCoupon) {
        tbCoupon.setCouponId(UUID.randomUUID().toString());
        tbCouponMapper.insert(tbCoupon);
    }

    /**
     * 删除
     */
    public void removeCoupon(String couponId) {
        TbCouponExample example = new TbCouponExample();
        TbCouponExample.Criteria criteria = example.createCriteria();
        criteria.andCouponIdEqualTo(couponId);
        tbCouponMapper.deleteByExample(example);
    }

    /**
     * 查询所有有效期内的 --- 无其他条件
     *
     * @return
     */
    public List<TbCoupon> queryCouponList() {
        TbCouponExample example = new TbCouponExample();
        TbCouponExample.Criteria criteria = example.createCriteria();
        criteria.andStartTimeLessThanOrEqualTo(new Date());
        criteria.andEndTimeGreaterThanOrEqualTo(new Date());
        List<TbCoupon> tbCoupons = tbCouponMapper.selectByExample(example);
        return tbCoupons;
    }

    /**
     * 查询优惠券内容
     * TODO 缓存呢？怎么加？
     *
     * @return 优惠券内容
     */
    public TbCoupon queryCouponById(String couponId) {
        TbCouponExample example = new TbCouponExample();
        TbCouponExample.Criteria criteria = example.createCriteria();
        criteria.andCouponIdEqualTo(couponId);
        List<TbCoupon> tbCoupons = tbCouponMapper.selectByExample(example);
        return tbCoupons == null ? null : tbCoupons.get(0);
    }
}

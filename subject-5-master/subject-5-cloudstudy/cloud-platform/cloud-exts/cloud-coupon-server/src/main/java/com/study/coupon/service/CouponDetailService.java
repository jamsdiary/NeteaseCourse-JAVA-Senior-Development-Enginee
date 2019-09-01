package com.study.coupon.service;

import com.study.coupon.bean.TbCouponDetail;
import com.study.coupon.bean.TbCouponDetailExample;
import com.study.coupon.dao.TbCouponDetailMapper;
import com.study.coupon.dao.TbCouponMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.study.coupon.common.CouponConstants.CouponDetailStatusEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CouponDetailService {

    @Autowired
    TbCouponDetailMapper tbCouponDetailMapper;

    @Autowired
    TbCouponMapper tbCouponMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 将订单与该用户对应的优惠券绑定
     *
     * @param couponDetailId 用户领取的优惠券记录
     * @param orderId        订单号
     * @param userId         用户ID
     */
    @Hmily(confirmMethod = "confirmLockCouponDetail", cancelMethod = "canceLockCouponDetail")
    public TbCouponDetail lockCouponDetail(String couponDetailId, String orderId, String userId) {
        TbCouponDetailExample example = new TbCouponDetailExample();
        TbCouponDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCouponDetailIdEqualTo(couponDetailId);
        criteria.andCouponDetailStatusEqualTo(CouponDetailStatusEnum.UnUsed.getStatus());
        criteria.andUserIdEqualTo(Integer.valueOf(userId));

        TbCouponDetail tbCouponDetailUpdate = new TbCouponDetail();
        tbCouponDetailUpdate.setOrderId(orderId);
        tbCouponDetailUpdate.setCouponDetailStatus(CouponDetailStatusEnum.Used.getStatus());
        int result = tbCouponDetailMapper.updateByExampleSelective(tbCouponDetailUpdate, example);
        if (result == 1) { // 等于1代表修改成功
            // 查询优惠详情
            return tbCouponDetailMapper.selectByPrimaryKey(couponDetailId);
        }
        return null;
    }

    public void confirmLockCouponDetail(String couponDetailId, String orderId, String userId) {
        // 课后作业
        log.info("=========进行订单confirm操作完成================");
    }

    public void canceLockCouponDetail(String couponDetailId, String orderId, String userId) {
        // 课后作业
        log.info("=========进行订单cancel操作完成================");
    }

    /**
     * 将订单与该用户对应的优惠券解除绑定
     *
     * @param couponDetailId 用户领取的优惠券记录
     * @param orderId        订单号
     * @param userId         用户ID
     */
    public TbCouponDetail releaseCouponDetail(String couponDetailId, String orderId, String userId) {
        TbCouponDetailExample example = new TbCouponDetailExample();
        TbCouponDetailExample.Criteria criteria = example.createCriteria();
        criteria.andCouponDetailIdEqualTo(couponDetailId);
        criteria.andOrderIdEqualTo(orderId);
        criteria.andCouponDetailStatusEqualTo(CouponDetailStatusEnum.Used.getStatus());
        criteria.andUserIdEqualTo(Integer.valueOf(userId));

        TbCouponDetail tbCouponDetailUpdate = new TbCouponDetail();
        tbCouponDetailUpdate.setCouponDetailStatus(CouponDetailStatusEnum.UnUsed.getStatus());
        int result = tbCouponDetailMapper.updateByExampleSelective(tbCouponDetailUpdate, example);
        if (result == 1) { // 等于1代表修改成功
            // 查询优惠详情
            return tbCouponDetailMapper.selectByPrimaryKey(couponDetailId);
        }
        return null;
    }

    /**
     * 能正常使用的优惠券（具体规则根据产品需求决定）
     *
     * @param userId
     * @return
     */
    public List<TbCouponDetail> getUnUsedCouponDetailByUserId(String userId) {
        TbCouponDetailExample example = new TbCouponDetailExample();
        TbCouponDetailExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(Integer.valueOf(userId));
        criteria.andCouponDetailStatusEqualTo(CouponDetailStatusEnum.UnUsed.getStatus());
        List<TbCouponDetail> tbCouponDetails = tbCouponDetailMapper.selectByExample(example);
        return tbCouponDetails;
    }
}

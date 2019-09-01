package com.study.coupon.dao;

import com.study.coupon.bean.TbCoupon;
import com.study.coupon.bean.TbCouponExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TbCouponMapper {
    int countByExample(TbCouponExample example);

    int deleteByExample(TbCouponExample example);

    int insert(TbCoupon record);

    int insertSelective(TbCoupon record);

    List<TbCoupon> selectByExample(TbCouponExample example);

    int updateByExampleSelective(@Param("record") TbCoupon record, @Param("example") TbCouponExample example);

    int updateByExample(@Param("record") TbCoupon record, @Param("example") TbCouponExample example);

    // 时间范围内，数量够
    @Update("update tb_coupon set coupon_sum = coupon_sum - 1 where  coupon_id=#{couponId}  and coupon_sum > 0 " +
            " and now() >= start_time and now() <= end_time")
    int acquire(String couponId);
}
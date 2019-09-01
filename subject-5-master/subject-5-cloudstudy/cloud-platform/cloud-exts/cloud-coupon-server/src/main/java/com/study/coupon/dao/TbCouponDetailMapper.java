package com.study.coupon.dao;

import com.study.coupon.bean.TbCouponDetail;
import com.study.coupon.bean.TbCouponDetailExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbCouponDetailMapper {
    int countByExample(TbCouponDetailExample example);

    int deleteByExample(TbCouponDetailExample example);

    int deleteByPrimaryKey(String couponDetailId);

    int insert(TbCouponDetail record);

    int insertSelective(TbCouponDetail record);

    List<TbCouponDetail> selectByExample(TbCouponDetailExample example);

    TbCouponDetail selectByPrimaryKey(String couponDetailId);

    int updateByExampleSelective(@Param("record") TbCouponDetail record, @Param("example") TbCouponDetailExample example);

    int updateByExample(@Param("record") TbCouponDetail record, @Param("example") TbCouponDetailExample example);

    int updateByPrimaryKeySelective(TbCouponDetail record);

    int updateByPrimaryKey(TbCouponDetail record);
}
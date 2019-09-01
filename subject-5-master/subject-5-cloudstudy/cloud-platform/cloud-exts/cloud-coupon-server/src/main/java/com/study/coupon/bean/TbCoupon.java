package com.study.coupon.bean;

import java.util.Date;

public class TbCoupon {
    private String couponId;

    private String couponType;

    private String couponContent;

    private Date startTime;

    private Date endTime;

    private Integer couponSum;

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType == null ? null : couponType.trim();
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent == null ? null : couponContent.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCouponSum() {
        return couponSum;
    }

    public void setCouponSum(Integer couponSum) {
        this.couponSum = couponSum;
    }
}
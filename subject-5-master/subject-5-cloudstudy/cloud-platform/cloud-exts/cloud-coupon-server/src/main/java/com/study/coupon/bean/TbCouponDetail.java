package com.study.coupon.bean;

import java.util.Date;

public class TbCouponDetail {
    private String couponDetailId;

    private String couponId;

    private String couponDetailStatus;

    private Integer userId;

    private String orderId;

    private Date startTime;

    private Date endTime;

    public String getCouponDetailId() {
        return couponDetailId;
    }

    public void setCouponDetailId(String couponDetailId) {
        this.couponDetailId = couponDetailId == null ? null : couponDetailId.trim();
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId == null ? null : couponId.trim();
    }

    public String getCouponDetailStatus() {
        return couponDetailStatus;
    }

    public void setCouponDetailStatus(String couponDetailStatus) {
        this.couponDetailStatus = couponDetailStatus == null ? null : couponDetailStatus.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
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
}
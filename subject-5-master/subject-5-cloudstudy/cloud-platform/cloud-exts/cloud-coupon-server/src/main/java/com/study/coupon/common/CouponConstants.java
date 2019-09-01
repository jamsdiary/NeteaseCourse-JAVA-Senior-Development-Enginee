package com.study.coupon.common;

public class CouponConstants {
    /**
     * 1-未使用 2-已使用 3-已过期 4-锁定  99-异常
     */
    public static enum CouponDetailStatusEnum {
        UnUsed("1"), Used("2"), Lock("3"), Exp("4"), Exception("99");

        public String getStatus() {
            return status;
        }

        private final String status;

        private CouponDetailStatusEnum(String status) {
            this.status = status;
        }


    }
}

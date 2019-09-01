package com.study.order.client;

import com.study.order.bean.Course;
import com.study.order.bean.RespBean;
import com.study.order.bean.UserStudy;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * 优惠卷 rpc
 */
@FeignClient(value = "cloud-coupon-server")
public interface CouponClient {

    @GetMapping(value = "/coupon/detail/op/lockCoupon")
    @Hmily
    public Object lockCoupon(String couponDetailId, String orderId, String orderUserId);

}



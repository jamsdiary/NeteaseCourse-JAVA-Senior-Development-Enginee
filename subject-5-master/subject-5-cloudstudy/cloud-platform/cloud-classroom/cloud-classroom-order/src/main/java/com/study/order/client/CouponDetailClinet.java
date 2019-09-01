package com.study.order.client;

import com.study.security.common.msg.TableResultResponse;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName CouponDetailClinet
 * @Description 优惠券详情
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/7/26 22:45
 * @Version 1.0
 */
@FeignClient(value = "cloud-coupon-server")
public interface CouponDetailClinet {

    @GetMapping(value = "/coupon/detail/op/getCouponDetail")
    public TableResultResponse getCouponDetailByUserId();

}

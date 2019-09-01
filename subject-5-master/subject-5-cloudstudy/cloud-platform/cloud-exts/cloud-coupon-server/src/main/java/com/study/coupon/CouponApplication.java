package com.study.coupon;

import com.study.security.auth.client.EnableClientAuthClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 优惠券和红包技术本质一样<br/>
 * <b>技术难点：</b>如何支撑高并发抢购，判断重复领取<br/>
 * 99%的公司而言，订单支付时使用优惠券是一个低频应用。
 * 就算是11.11，6.18仅阿里、京东量比较大，大部分公司还是在几K的情况。总人口决定系统的使用人数不会出现飙升<br/>
 * 核心步骤：
 * 0. 开启时间一般是整点 00，30，这样的形式。所以定期加载到redis进行限流即可
 * 1. 领取优惠 （根据登录人发放或者判重）
 * 2. 锁定优惠 （无效、已被锁定、已使用的优惠券不能使用），别人的优惠券不能使用，一个订单只能用一张券
 * 3. 使用优惠，记录对应的订单号
 * 4. 优惠券过期采用任务调度即可，通常都是整天计算的。 同时，对于不再需要的数据，可以采取历史数据隔离的方式，把数据挪走
 *
 * 定时任务调度框架：quartz xxl-job TBSchedule elastic-job
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableDiscoveryClient
//@EnableScheduling
@EnableFeignClients({"com.study.security.auth.client.feign"})
@ImportResource({"classpath:applicationContext.xml"})
@EnableClientAuthClient
public class CouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }
}

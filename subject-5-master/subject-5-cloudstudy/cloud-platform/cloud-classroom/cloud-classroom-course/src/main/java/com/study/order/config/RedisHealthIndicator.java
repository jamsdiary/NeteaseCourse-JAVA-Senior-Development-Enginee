package com.study.order.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 *@描述 spring cloud中 admin、eureka中先不检测redis的状态
 *@创建人
 *@创建时间  2019/1/21
 *@修改人和其它信息
 */
@Component
public class RedisHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up().build();
    }

}
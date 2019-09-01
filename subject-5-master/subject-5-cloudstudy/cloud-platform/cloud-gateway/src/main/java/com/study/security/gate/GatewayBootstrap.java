package com.study.security.gate;

import com.study.security.auth.client.EnableClientAuthClient;
import com.study.security.gate.utils.DBLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName GatewayServerBootstrap
 * @Description 网关主程序
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableClientAuthClient
@EnableFeignClients({"com.study.security.auth.client.feign","com.study.security.gate.feign"})
public class GatewayBootstrap {
    public static void main(String[] args) {
        DBLog.getInstance().start();
        SpringApplication.run(GatewayBootstrap.class, args);
    }
}

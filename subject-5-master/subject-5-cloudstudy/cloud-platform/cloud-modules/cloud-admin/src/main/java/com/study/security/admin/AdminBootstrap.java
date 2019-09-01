package com.study.security.admin;

import com.ace.cache.EnableAceCache;
import com.study.security.auth.client.EnableClientAuthClient;
import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName AdminBootstrap
 * @Description admin服务住程序
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@EnableDiscoveryClient
@RefreshScope
@EnableCircuitBreaker
@SpringBootApplication
@EnableFeignClients({"com.study.security.auth.client.feign"})
@EnableScheduling
@EnableClientAuthClient
@EnableAceCache
@EnableTransactionManagement
@MapperScan("com.study.security.admin.mapper")
@EnableSwagger2Doc
public class AdminBootstrap {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminBootstrap.class).run(args);    }
}

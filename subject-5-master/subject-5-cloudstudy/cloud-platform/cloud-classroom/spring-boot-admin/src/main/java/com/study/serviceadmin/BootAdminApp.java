package com.study.serviceadmin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @ClassName AdminServerApp
 * @Description spring-boot-admin监控 主程序
 * @see 官方文档：http://codecentric.github.io/spring-boot-admin/2.0.0/
 *      使用文档：https://blog.csdn.net/hubo_88/article/details/80671192
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/6/11 15:39
 * @Version 1.0
 */
@EnableAdminServer
@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
public class BootAdminApp {

    public static void main(String[] args) {
        SpringApplication.run( BootAdminApp.class, args );
    }

}




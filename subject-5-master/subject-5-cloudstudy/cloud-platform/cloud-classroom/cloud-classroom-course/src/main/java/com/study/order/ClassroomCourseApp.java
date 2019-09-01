package com.study.order;

import com.study.security.auth.client.EnableClientAuthClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@RefreshScope
@EnableDiscoveryClient
@EnableFeignClients({"com.study.security.auth.client.feign", "com.study.order.client"})
@EnableClientAuthClient
@MapperScan("com.study.order.mapper")
public class ClassroomCourseApp {

	public static void main(String[] args) {
		SpringApplication.run(ClassroomCourseApp.class, args);

	}
}

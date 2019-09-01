package com.study.redis.lock.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiaoshaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiaoshaDemoApplication.class, args);
		System.out.println("分布式锁示例系统启动完毕...");
	}
}

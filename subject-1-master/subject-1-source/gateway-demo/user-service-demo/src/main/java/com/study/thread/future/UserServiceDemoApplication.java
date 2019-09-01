package com.study.thread.future;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class UserServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceDemoApplication.class, args);
	}

	@RequestMapping(path = "/userinfo-api/get", produces = "application/json; charset=UTF-8")
	public String getInfo(String userId) throws InterruptedException {
		Thread.sleep(2000L);
		return "{\"userId\":\"" + userId + "\",\"address\":\"Hang Zhou\",\"age\":19,\"userName\":\"张峰\"}";
	}

	@RequestMapping(path = "/integral-api/get", produces = "application/json; charset=UTF-8")
	public String getIntegral(String userId) throws InterruptedException {
		Thread.sleep(3000L);
		return "{\"userId\":\"" + userId + "\",\"intergral\":99}";
	}
}

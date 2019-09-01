package com.study.mike.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.study.mike.dubbo.DemoService;

public class Consumer {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
		context.start();

		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

		System.out.println();
		System.out.println(demoService);
		context.close();
	}
}

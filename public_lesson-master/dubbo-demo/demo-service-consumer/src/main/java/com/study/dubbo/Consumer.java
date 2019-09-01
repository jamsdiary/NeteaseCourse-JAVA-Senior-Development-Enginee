package com.study.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "consumer.xml" });
		context.start();
		// 获取dubbo注入的远程调用代理对象
		DemoService demoService = (DemoService) context.getBean("demoService");
		// 执行远程调用
		String result = demoService.test("123");
		// 展示调用结果
		System.out.println("调用结果####>>>>>" + result);

	}
}
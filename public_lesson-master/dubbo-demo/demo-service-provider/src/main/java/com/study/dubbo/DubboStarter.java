package com.study.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动服务提供者
 */
public class DubboStarter {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "provider.xml" });
		context.start();
		System.in.read(); // 按任意键退出
		context.close();
	}
}

package com.study.mike.dubbo.provider;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

//@Configuration
@EnableDubbo(scanBasePackages = "com.study.mike.dubbo.provider ")
@PropertySource("classpath:/dubbo/dubbo-provider.properties")
public class AnnotationProviderConfiguration {
	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				AnnotationProviderConfiguration.class);
		context.start();
		System.in.read(); // 按任意键退出
		context.close();
	}
}

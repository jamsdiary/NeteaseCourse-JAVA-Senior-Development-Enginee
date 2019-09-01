package com.study.mike.dubbo.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

//@Configuration
@EnableDubbo(scanBasePackages = "com.study.mike.dubbo.consumer")
@PropertySource("classpath:/dubbo/dubbo-consumer.properties")
@ComponentScan(value = { "com.study.mike.dubbo.consumer" })
public class AnnotationConsumerConfiguration {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				AnnotationConsumerConfiguration.class);
		context.start();
		final AnnotationDemoAction annotationAction = context.getBean(AnnotationDemoAction.class);
		String hello = annotationAction.doSayHello("world");
		System.out.println(hello);
		context.close();
	}
}

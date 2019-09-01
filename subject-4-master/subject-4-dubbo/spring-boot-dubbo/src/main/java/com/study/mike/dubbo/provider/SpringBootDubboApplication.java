package com.study.mike.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.dubbo.remoting.http.servlet.DispatcherServlet;

@SpringBootApplication
// @EnableDubbo(scanBasePackages = "com.study.mike.dubbo.provider")
public class SpringBootDubboApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDubboApplication.class, args);
	}

	//@Bean
/*	public ServletRegistrationBean<DispatcherServlet> getServletRegistrationBean() {

		ServletRegistrationBean<DispatcherServlet> bean = new ServletRegistrationBean<DispatcherServlet>(
				new DispatcherServlet());
		bean.addUrlMappings("/*");
		bean.setLoadOnStartup(1);

		return bean;
	}*/

}

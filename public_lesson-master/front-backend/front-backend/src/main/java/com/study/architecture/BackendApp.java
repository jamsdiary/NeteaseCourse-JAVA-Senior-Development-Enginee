package com.study.architecture;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 后端接口应用
 * 
 * @author Tony
 *
 */
@SpringBootApplication
public class BackendApp {
	public static void main(String[] args) {
		new SpringApplicationBuilder(BackendApp.class).web(WebApplicationType.SERVLET).run(args);
	}

	// 增加一个准入机制。 当收到浏览器的option预检询问请求的时候，根据这个规则，返回给浏览器信息
	@Bean
	public WebMvcConfigurer corsWebMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/user/**").allowedOrigins("http://www.tony.com");
			}
		};
	}
}

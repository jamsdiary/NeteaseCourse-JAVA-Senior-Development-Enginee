package com.study.cache.nginx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
public class NginxCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NginxCacheDemoApplication.class, args);
	}

	/**
	 * 增加一个filter，实现浏览器缓存(http status 304的原理)，如果etag内容和请求中携带的etag相同，则认为数据无变化返回304
	 */
	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
}

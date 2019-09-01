package com.study.essearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @ClassName ESCofnig
 * @Description 基础包的注释驱动配置，配置自动扫描的repositories根目录
 * @Author 网易云课堂微专业-java高级开发工程师
 * @Date 2019/7/25 23:02
 * @Version 1.0
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.study.essearch.repository")
public class ESConfig {

}

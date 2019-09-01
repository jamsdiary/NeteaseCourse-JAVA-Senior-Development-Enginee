package com.study.springboot.demo1;

import com.study.springboot.demo1.properties.MyPropertiesBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.Properties;

@SpringBootApplication
@ServletComponentScan
// @EnableConfigurationProperties(value = {MyPropertiesBean.class})
public class Example {

    public static void main(String[] args) {
        // SpringApplication.run(Example.class, args);

        // 通过SpringApplication设置参数配置
        SpringApplication springApplication = new SpringApplication(Example.class);
        springApplication.setAdditionalProfiles();
        Properties properties = new Properties();
        properties.setProperty("name", "setDefaultProperties-tony");
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }
}

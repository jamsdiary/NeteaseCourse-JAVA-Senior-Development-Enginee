package com.study.springboot.demo1.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 可以注入 K/V, list, map
@Component
@ConfigurationProperties(prefix = "com.study")
public class MyPropertiesBean {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

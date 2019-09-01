package com.study.springboot.demo1;

import com.study.springboot.demo1.properties.MyPropertiesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    // 冒号后面，就是一个默认值
    @Value("${name:default-tony}")
    public String name;

    @Autowired
    MyPropertiesBean myPropertiesBean;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("启动完毕，读取到配置：" + name);
        System.out.println("启动完毕，读取到myPropertiesBean配置：" + myPropertiesBean.getUserName());
    }
}

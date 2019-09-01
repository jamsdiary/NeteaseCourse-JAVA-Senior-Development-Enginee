package com.study.springboot.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @Autowired
    Environment environment;

    @RequestMapping("/index")
    public String index() {
        return environment.getProperty("name");
    }

    @RequestMapping("/test-errorpage")
    public String errorpage() {
        int i = 1 / 0;// 特意报错
        return environment.getProperty("name");
    }
}

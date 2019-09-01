package com.study.springboot.demo1.actuator.jmx;

import org.springframework.stereotype.Component;

@Component
public class JmxTest implements JmxTestMBean {
    private String name;
     
    @Override
    public String getName() {
        return name;
    }
  
    @Override
    public void setName(String name) {
        this.name = name;
    }
  
    @Override
    public String printHello() {
        return "JmxTest "+ name;
    }
  
    @Override
    public String printHello(String whoName) {
        return "JmxTest  " + whoName;
    }
}
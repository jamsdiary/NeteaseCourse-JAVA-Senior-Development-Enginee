package com.study.mike.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.study.mike.dubbo.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	public String sayHello(String name) {
		System.out.println("*********************** " + name);
		return "Hello " + name;
	}
}

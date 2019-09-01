package com.study.dubbo;

/** 具体的实现类 */
public class DemoServiceImpl implements DemoService {

	public String test(String value) {
		return value + ">>>>>>>>result";
	}

}

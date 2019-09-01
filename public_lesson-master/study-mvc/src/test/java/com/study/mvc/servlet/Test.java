package com.study.mvc.servlet;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		Map<String, Object> test = new HashMap<>();
		Map<String, Object> a = new HashMap<>();
		Map<String, Object> b = new HashMap<>();
		
		a.put("aa", "aaaa");
		a.put("bb", "bbbb"); 
		a.put("cc", "cccc");
		a.put("dd", "dddd");
		
		b.put("11", "aaaa");
		b.put("22", "bbbb"); 
		b.put("33", "cccc");
		b.put("44", "dddd");
		
		test.putAll(a);
		test.putAll(b);
		System.out.println(test.size());
	}
}

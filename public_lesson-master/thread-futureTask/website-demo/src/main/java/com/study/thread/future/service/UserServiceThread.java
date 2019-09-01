package com.study.thread.future.service;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 *  多线程并行调用http接口
 * 
 */
@Service
public class UserServiceThread {

	@Autowired
	private RestTemplate restTemplate;

	/** 查询多个系统的数据，合并返回 
	 * @throws Exception 
	 * @throws InterruptedException */
	public Object getUserInfo(String userId) throws InterruptedException, Exception {
		// 多线程 调用接口, 并且拿到返回值
		new Thread(new Runnable() {
			@Override
			public void run() {
			}
		}).start();
		// Runnable \ Callable：方法名不同、返回值、可以抛出异常
		// 还有紧密的联系
		// 1、 封装业务操作
		Callable<JSONObject> userinfoCallable = new Callable<JSONObject>() { 
			@Override
			public JSONObject call() throws Exception {
				// 1. 先从调用获取用户基础信息的http接口
				long userinfoTime = System.currentTimeMillis();
				String value = restTemplate.getForObject("http://www.tony.com/userinfo-api/get?userId=" + userId, String.class);
				JSONObject userInfo = JSONObject.parseObject(value);
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + " >   userinfo-api用户基本信息接口调用时间为" + (System.currentTimeMillis() - userinfoTime));
				return userInfo;
			}
		};

		Callable<JSONObject> intergralCallable = new Callable<JSONObject>() { 
			@Override
			public JSONObject call() throws Exception {
				// 2. 再调用获取用户积分信息的接口
				long integralApiTime = System.currentTimeMillis();
				String intergral = restTemplate.getForObject("http://www.tony.com/integral-api/get?userId=" + userId,
						String.class);
				JSONObject intergralInfo = JSONObject.parseObject(intergral);
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + " >   integral-api积分接口调用时间为" + (System.currentTimeMillis() - integralApiTime));
				return intergralInfo;
			}
		};
		
		// 原味代码，有助于同学们去理解底层具体实现
		// 2、 futureTask封装callable
		TonyFutureTask<JSONObject> userinfoTask = new TonyFutureTask<>(userinfoCallable);
		TonyFutureTask<JSONObject> intergralTask = new TonyFutureTask<>(intergralCallable);
		
		// 3、 多线程运行这个futureTask业务封装
		new Thread(userinfoTask).start();
		new Thread(intergralTask).start();
		
		// 4. 合并为一个json对象
		JSONObject result = new JSONObject();
		// get 方法获取返回。 获取callable的执行结果.  等待执行结果
		result.putAll(userinfoTask.get());
		result.putAll(intergralTask.get());
		
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " >   全部调用完毕");
		return result;
	}

}

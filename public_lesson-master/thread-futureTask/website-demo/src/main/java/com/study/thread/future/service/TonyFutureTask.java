package com.study.thread.future.service;

import java.util.concurrent.Callable;

// FutureTask原理
// 已知条件
// 1、 构造函数传递 callable
// 2、 有泛型定义
// 3、 是runnable实现
// 4、 get方法，可以获取callable执行结果
// 5、 等待的效果，阻塞线程效果\
// 自己写一个futureTask
public class TonyFutureTask<T> implements Runnable {
	Callable<T> callable; // 具体的业务封装在callable
	T result; // 执行结果
	String state = "NEW"; // task执行状态

	public TonyFutureTask(Callable<T> callable) {
		this.callable = callable;
	}

	// 多线程实际执行的方法
	@Override
	public void run() {
		try {
			result = callable.call();
		} catch (Exception e) {
			// state = "exception";
			e.printStackTrace();
		} finally {
			state = "END";
		}

		synchronized (this) {
			System.out.println(Thread.currentThread().getName() + " > 执行结束， 通知等待者");
			this.notifyAll();
		}
	}

	public T get() throws InterruptedException {
		// 如果没有执行结束，我就等待
		if ("END".equals(state)) {
			return result;
		}

		// 等待
		System.out.println(Thread.currentThread().getName() + " > 进入等待，还没执行完");
		// wait/notify
		synchronized (this) {
			while (!"END".equals(state)) {
				this.wait();
			}
		}

		return result;
	}
}

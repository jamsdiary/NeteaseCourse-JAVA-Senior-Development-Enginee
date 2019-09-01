package com.study.thread.future;

import java.util.concurrent.locks.LockSupport;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.study.thread.future.service.UserService;
import com.study.thread.future.service.UserServiceThread;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebsiteDemoApplication.class)
public class WebsiteDemoApplicationTests {

	@Before
	public void start() {
		System.out.println("开始测试");
	}

	@After
	public void end() {
		System.out.println("结束测试");
	}

	@Test
	public void ex() {
		int x = 0, y =100;
		System.out.println("结果" + (x + y));
		int i = 1/0;
	}
	
	@Autowired
	UserService userService;

	@Test
	public void testUserSerivce() {
		// 调用
		long currentTimeMillis = System.currentTimeMillis();
		// http 实际就是 线程 调用service
		Object userInfo = userService.getUserInfo("tony");

		System.out.println("getUserInfo总执行时间为" + (System.currentTimeMillis() - currentTimeMillis));
		System.out.println(userInfo.toString());
	}
	
	@Autowired
	UserServiceThread userServiceThread;
	
	@Test
	public void testUserSerivceThread() throws InterruptedException, Exception {
		// 调用
		long currentTimeMillis = System.currentTimeMillis();
		// http 实际就是 线程 调用service
		Object userInfo = userServiceThread.getUserInfo("tony");
		
		System.out.println("getUserInfo总执行时间为" + (System.currentTimeMillis() - currentTimeMillis));
		System.out.println(userInfo.toString());
	}

	@Test
	public void waitnotifytest() throws InterruptedException {
		// LockSupport.park  / unpark
		// 启动一个线程
		new Thread(() -> {
			try {
				Thread.sleep(5000L);
				synchronized (this) {
					System.out.println("1、 进入等待");
					this.wait();
					System.out.println("继续执行");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		// 3秒之后唤醒
		Thread.sleep(3000L);
		synchronized (this) {
			this.notifyAll();
			System.out.println("2、 唤醒等待者");
		}
		
		Thread.sleep(10000000L);
	}

}

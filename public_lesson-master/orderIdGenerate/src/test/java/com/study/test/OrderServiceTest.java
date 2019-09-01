package com.study.test;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.study.service.IOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-servlet.xml" })
@TransactionConfiguration(defaultRollback = true)
public class OrderServiceTest {

	@Autowired
	@Qualifier("uuidOrderServiceImpl")
//	@Qualifier("snowflakeOrderServiceImpl")
//	@Qualifier("redisOrderServiceImpl")
	private IOrderService orderService;

	private static final int threadNum = 100;
	// 线程同步工具
	private static CountDownLatch cdl = new CountDownLatch(threadNum); // 100 -1 =99 - 98 --- =0

	@Test
	public void test() throws Exception {
		System.out.println("============================test start");
		for (int i = 0; i < threadNum; i++) {
			new Thread(new orderThread()).start();
			cdl.countDown();// 计数， threadNum-1， =0时， 唤醒所有线程
		}
		Thread.currentThread();
		Thread.sleep(3000);
		System.out.println("============================test end");
	}

	class orderThread implements Runnable {
		public void run() {
			try {
				cdl.await(); // 等待线程同步执行
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			orderService.orderId();
		}
	}
}

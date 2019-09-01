package com.study.cache.stampeding;

import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.study.cache.stampeding.service.TicketService;
import com.study.cache.stampeding.service.TicketService2;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheStampedingApplicationTests {

	long timed = 0L;

	@Before
	public void start() {
		System.out.println("开始测试");
		timed = System.currentTimeMillis();
	}

	@After
	public void end() {
		System.out.println("结束测试,执行时长：" + (System.currentTimeMillis() - timed));
	}

	@Autowired
	TicketService ticketService;

	@Autowired
	TicketService2 ticketService2;

	// 车次
	private static final String TICKET_SEQ = "G296";

	// 模拟的请求数量
	private static final int THREAD_NUM = 2000;

	// 倒计数器 juc包中常用工具类
	private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

	@Test
	public void benchmark() throws InterruptedException {
		// 创建 并不是马上发起请求
		Thread[] threads = new Thread[THREAD_NUM];
		for (int i = 0; i < THREAD_NUM; i++) {
			// 多线程模拟用户查询请求
			Thread thread = new Thread(() -> {
				try {
					// 代码在这里等待，等待countDownLatch为0，代表所有线程都start，再运行后续的代码
					countDownLatch.await();
					// http请求，实际上就是多线程调用这个方法
					ticketService.queryTicketStock(TICKET_SEQ);

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			threads[i] = thread;

			thread.start();
			// 田径。启动后，倒计时器倒计数 减一，代表又有一个线程就绪了
			countDownLatch.countDown();
		}

		// 等待上面所有线程执行完毕之后，结束测试
		for (Thread thread : threads) {
			thread.join();
		}
	}

}

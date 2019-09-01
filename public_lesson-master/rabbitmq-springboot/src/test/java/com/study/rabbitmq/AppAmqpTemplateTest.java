package com.study.rabbitmq;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * jmeter
 * 模拟高并发访问controller，会出现什么情况？
 * 
 * @author allen
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppAmqpTemplateTest {

	// 线程数量
	public static final int THREAD_NUM = 300;
	
	RestTemplate restTemplate = new RestTemplate();

	/**
	 * 模拟下高并发下访问服务
	 */
	@Test
	public void concurrentTest() {
		// 初始化计数器为1
		CountDownLatch countDownLatch = new CountDownLatch(1);

		// 模拟200个并发请求
		for (int i = 0; i < THREAD_NUM; i++) {
			new Thread(new Run(countDownLatch)).start();
		}
		// 计数器減一 所有线程释放 并发访问。
		countDownLatch.countDown();
	}

	/**
	 * 线程类
	 */
	private class Run implements Runnable {
		private final CountDownLatch countDownLatch;

		public Run(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				// 线程等待
				countDownLatch.await(); // 一直阻塞当前线程，直到计时器的值为0
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// 调用接口
			ResponseEntity<String> result = restTemplate.getForEntity(
					"http://localhost:8080/rabbitmq/user/get/1",
					String.class);
			System.out.println(result);
		}
	}

}

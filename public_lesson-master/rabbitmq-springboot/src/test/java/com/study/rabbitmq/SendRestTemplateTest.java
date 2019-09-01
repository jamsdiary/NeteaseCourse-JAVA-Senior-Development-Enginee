package com.study.rabbitmq;

import com.study.rabbitmq.mq.RabbitConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SendRestTemplateTest {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Test
	public void sendMessage() {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		// 模拟300个并发请求
		for (int i = 0; i < 1000; i++) {
			new Thread(new Send(countDownLatch)).start();
			;
		}

		// 计数器減一 所有线程释放 并发访问。
		countDownLatch.countDown();
	}

	private class Send implements Runnable {
		private final CountDownLatch countDownLatch;

		public Send(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				countDownLatch.await(); // 一直阻塞当前线程，直到计时器的值为0
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String message = "1"; // 模拟获取用户id为1
			rabbitTemplate.convertAndSend(RabbitConfig.DIRECT_ROUTING_KEY_recvqueue, message);
		}

	}

}

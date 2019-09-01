package com.study.distribute.lock.sample;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ConcurrentTestDemo {

	public static void main(String[] args) {
		// 并发数
		int currency = 20;

		// 循环屏障
		CyclicBarrier cb = new CyclicBarrier(currency);

		OrderService orderService = new OrderServiceImpl();

		// 多线程模拟高并发
		for (int i = 0; i < currency; i++) {
			new Thread(new Runnable() {
				public void run() {

					System.out.println(Thread.currentThread().getName() + "---------我准备好---------------");
					// 等待一起出发
					try {
						cb.await();
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
					// 调用创建订单服务
					orderService.createOrder();
				}
			}).start();

		}
	}

}

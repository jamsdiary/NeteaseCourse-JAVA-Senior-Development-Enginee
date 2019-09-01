package com.study.concurrent.volat;

import java.util.concurrent.CountDownLatch;

public class AtomicityDemo {

	static volatile int count = 0;

	public static void increase() {
		count++;
	}

	public static void main(String[] args) {
		int threads = 20;
		CountDownLatch cdl = new CountDownLatch(threads);
		for (int i = 0; i < threads; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10000; i++) {
						AtomicityDemo.increase();
					}
					cdl.countDown();
				}
			}).start();
		}

		try {
			cdl.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(AtomicityDemo.count);
	}
}

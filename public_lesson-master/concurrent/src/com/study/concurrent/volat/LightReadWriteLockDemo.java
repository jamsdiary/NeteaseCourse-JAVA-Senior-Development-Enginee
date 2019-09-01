package com.study.concurrent.volat;

/**
 * 开销较低的读写锁策略 保证并发读到最新的值 通过对写加锁，保证线程安全
 */
public class LightReadWriteLockDemo {

	private volatile int value;

	public int getValue() {
		return value;
	}

	public synchronized int increment(int num) {
		return value += num;
	}
}

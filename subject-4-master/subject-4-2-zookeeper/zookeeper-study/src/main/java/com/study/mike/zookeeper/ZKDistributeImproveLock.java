package com.study.mike.zookeeper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

public class ZKDistributeImproveLock implements Lock {

	/*
	 * 利用临时顺序节点来实现分布式锁
	 * 获取锁：取排队号（创建自己的临时顺序节点），然后判断自己是否是最小号，如是，则获得锁；不是，则注册前一节点的watcher,阻塞等待
	 * 释放锁：删除自己创建的临时顺序节点
	 */
	private String lockPath;

	private ZkClient client;

	private ThreadLocal<String> currentPath = new ThreadLocal<>();

	private ThreadLocal<String> beforePath = new ThreadLocal<>();

	// 锁重入计数
	private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();

	public ZKDistributeImproveLock(String lockPath) {
		super();
		this.lockPath = lockPath;
		client = new ZkClient("localhost:2181");
		client.setZkSerializer(new MyZkSerializer());
		if (!this.client.exists(lockPath)) {
			try {
				this.client.createPersistent(lockPath);
			} catch (ZkNodeExistsException e) {

			}
		}
	}

	@Override
	public boolean tryLock() {
		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			if (count > 0) {
				this.reentrantCount.set(++count);
				return true;
			}
		}

		if (this.currentPath.get() == null) {
			currentPath.set(this.client.createEphemeralSequential(lockPath + "/", "aaa"));
		}
		// 获得所有的子
		List<String> children = this.client.getChildren(lockPath);

		// 排序list
		Collections.sort(children);

		// 判断当前节点是否是最小的
		if (currentPath.get().equals(lockPath + "/" + children.get(0))) {
			this.reentrantCount.set(1);
			return true;
		} else {
			// 取到前一个
			// 得到字节的索引号
			int curIndex = children.indexOf(currentPath.get().substring(lockPath.length() + 1));
			beforePath.set(lockPath + "/" + children.get(curIndex - 1));
		}
		return false;
	}

	@Override
	public void lock() {
		if (!tryLock()) {
			// 阻塞等待
			waitForLock();
			// 再次尝试加锁
			lock();
		}
	}

	private void waitForLock() {

		CountDownLatch cdl = new CountDownLatch(1);

		// 注册watcher
		IZkDataListener listener = new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("-----监听到节点被删除");
				cdl.countDown();
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {

			}
		};

		client.subscribeDataChanges(this.beforePath.get(), listener);

		// 怎么让自己阻塞
		if (this.client.exists(this.beforePath.get())) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 醒来后，取消watcher
		client.unsubscribeDataChanges(this.beforePath.get(), listener);
	}

	@Override
	public void unlock() {
		// 重入的释放锁处理
		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			if (count > 1) {
				this.reentrantCount.set(--count);
				return;
			} else {
				this.reentrantCount.set(null);
			}
		}
		// 删除节点
		this.client.delete(this.currentPath.get());
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		// 并发数
		int currency = 50;

		// 循环屏障
		CyclicBarrier cb = new CyclicBarrier(currency);

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
					ZKDistributeImproveLock lock = new ZKDistributeImproveLock("/distLock");

					try {
						lock.lock();
						System.out.println(Thread.currentThread().getName() + " 获得锁！");
					} finally {
						lock.unlock();
					}
				}
			}).start();

		}
	}

}

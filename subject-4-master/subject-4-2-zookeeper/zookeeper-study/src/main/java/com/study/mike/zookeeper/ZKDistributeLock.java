package com.study.mike.zookeeper;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

public class ZKDistributeLock implements Lock {

	private String lockPath;

	private ZkClient client;

	// 锁重入计数
	private ThreadLocal<Integer> reentrantCount = new ThreadLocal<>();

	public ZKDistributeLock(String lockPath) {
		super();
		this.lockPath = lockPath;

		client = new ZkClient("localhost:2181");
		client.setZkSerializer(new MyZkSerializer());
	}

	@Override
	public boolean tryLock() { // 不会阻塞

		if (this.reentrantCount.get() != null) {
			int count = this.reentrantCount.get();
			if (count > 0) {
				this.reentrantCount.set(++count);
				return true;
			}
		}
		// 创建节点
		try {
			client.createEphemeral(lockPath);
			this.reentrantCount.set(1);
		} catch (ZkNodeExistsException e) {
			return false;
		}
		return true;
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
		client.delete(lockPath);
	}

	@Override
	public void lock() { // 如果获取不到锁，阻塞等待
		if (!tryLock()) {
			// 没获得锁，阻塞自己
			waitForLock();
			// 再次尝试
			lock();
		}

	}

	private void waitForLock() {

		CountDownLatch cdl = new CountDownLatch(1);

		IZkDataListener listener = new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("----收到节点被删除了-------------");
				cdl.countDown();
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
			}
		};

		client.subscribeDataChanges(lockPath, listener);

		// 阻塞自己
		if (this.client.exists(lockPath)) {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 取消注册
		client.unsubscribeDataChanges(lockPath, listener);
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
					ZKDistributeLock lock = new ZKDistributeLock("/distLock11");

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

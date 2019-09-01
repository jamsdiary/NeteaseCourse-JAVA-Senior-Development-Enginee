package com.study.distribute.lock.zk;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import com.study.distribute.lock.sample.MyZkSerializer;

public class ZKDistributeLock implements Lock {

	private String lockPath;

	private ZkClient client;

	public ZKDistributeLock(String lockPath) {
		super();
		this.lockPath = lockPath;

		client = new ZkClient("localhost:2181");
		client.setZkSerializer(new MyZkSerializer());
	}

	@Override
	public boolean tryLock() { // 不会阻塞
		// 创建节点
		try {
			client.createEphemeral(lockPath);
		} catch (ZkNodeExistsException e) {
			return false;
		}
		return true;
	}

	@Override
	public void unlock() {
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
			public void handleDataChange(String dataPath, Object data)
					throws Exception {
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
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}

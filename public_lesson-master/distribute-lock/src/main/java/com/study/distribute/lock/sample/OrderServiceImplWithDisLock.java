package com.study.distribute.lock.sample;

import java.util.concurrent.locks.Lock;

import com.study.distribute.lock.zk.ZKDistributeImproveLock;

public class OrderServiceImplWithDisLock implements OrderService {

	// 用static修饰来模拟共用一个订单编号服务
	private static OrderCodeGenerator ocg = new OrderCodeGenerator();

	// 创建订单接口
	@Override
	public void createOrder() {
		String orderCode = null;
		// 分布式锁
		Lock lock = new ZKDistributeImproveLock("/dabaojian66");
		try {
			lock.lock();
			// 获取订单号
			orderCode = ocg.getOrderCode();

		} finally {
			lock.unlock();
		}

		System.out.println(Thread.currentThread().getName() + " =============>" + orderCode);

		// ……业务代码，此处省略100行代码

	}

}

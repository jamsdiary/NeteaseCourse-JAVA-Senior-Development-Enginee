package com.study.distribute.lock.sample;

public class OrderServiceImpl implements OrderService {

	private OrderCodeGenerator ocg = new OrderCodeGenerator();

	// 创建订单接口
	@Override
	public void createOrder() {

		// 获取订单号
		String orderCode = ocg.getOrderCode();

		System.out.println(Thread.currentThread().getName() + " =============>"
				+ orderCode);

		// ……业务代码，此处省略100行代码

	}

}

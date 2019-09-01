package com.study.transaction.order.test;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.study.transaction.order.OrderApplication;
import com.study.transaction.order.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderApplicationTests {
	@Before
	public void start() {
		System.out.println("开始测试##############");
	}

	@After
	public void finish() {
		System.out.println("结束##############");
	}

	@Autowired
	OrderService orderService;

	@Test
	public void orderCreate() throws Exception {
		// 订单号生成
		String orderId = UUID.randomUUID().toString();
		JSONObject orderInfo = new JSONObject();
		orderInfo.put("orderId", orderId);
		orderInfo.put("userId", "tony");
		orderInfo.put("orderContent", "买了沙县小吃");
		orderService.createOrder(orderInfo);
		System.out.println("订单创建成功");
	}

}

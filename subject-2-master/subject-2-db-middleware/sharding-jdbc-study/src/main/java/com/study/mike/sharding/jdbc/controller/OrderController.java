package com.study.mike.sharding.jdbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.mike.sharding.jdbc.model.Order;
import com.study.mike.sharding.jdbc.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("add")
	public Order addOrder(Order o) {
		
		this.orderService.addOrder(o);
		
		return o;
	}
	
	@RequestMapping("get")
	public Order getOrder(Long orderId) {
		return this.orderService.getOrder(orderId);
	}
}

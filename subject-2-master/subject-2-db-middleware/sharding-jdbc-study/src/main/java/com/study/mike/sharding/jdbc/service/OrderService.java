package com.study.mike.sharding.jdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.mike.sharding.jdbc.dao.OrderDao;
import com.study.mike.sharding.jdbc.model.Order;

@Service
public class OrderService {

	@Autowired
	private OrderDao orderDao;
	
	public void addOrder(Order o) {
		this.orderDao.addOrder(o);
	}
	
	public Order getOrder(Long orderId) {
		return this.orderDao.get(orderId);
	}
}

package com.study.mike.sharding.jdbc.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Order {

	private Long orderId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date orderTime;
	
	private Long customerId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}

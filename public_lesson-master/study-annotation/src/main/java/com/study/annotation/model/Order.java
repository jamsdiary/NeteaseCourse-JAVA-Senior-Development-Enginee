package com.study.annotation.model;

import java.io.Serializable;

import com.study.annotation.annotation.SetValue;
import com.study.annotation.mapper.UserMapper;

/**
 * 订单实体类
 * @author allen
 */
public class Order implements Serializable {
	private static final long serialVersionUID = -8821751371277937944L;
	
	// 订单id
	private String id;
	
	// 订单管理的用户id
	private String customerId;

	// 订单用户名
	@SetValue(beanClass = UserMapper.class, method = "find", paramField = "customerId", targetField = "userName")
	private String customerName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}

package com.study.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.study.service.IOrderService;

/**
 * 订单号-jdk uuid实现
 * 
 * @author allen
 *
 */
@Service("uuidOrderServiceImpl")
public class UuidOrderServiceImpl implements IOrderService {

	/**
	 * 产生订单号
	 * d24e2b8a-598c-49e3-a31b-dd3de41af20b
	 */
	@Override
	public void orderId() {
		System.out.println("insert into order_id(id) values('" + UUID.randomUUID() + "');");
	}

}

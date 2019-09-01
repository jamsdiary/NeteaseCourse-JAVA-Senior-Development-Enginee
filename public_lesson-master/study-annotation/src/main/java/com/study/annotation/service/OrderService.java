package com.study.annotation.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.study.annotation.annotation.NeedSetFieldValue;
import com.study.annotation.mapper.OrderMapper;
import com.study.annotation.mapper.UserMapper;
import com.study.annotation.model.Order;
import com.study.annotation.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询订单分页
	 * 
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@NeedSetFieldValue
	public Page<Order> pageQuery(String customerId, int pageNum, int pageSize) {
		Page<Order> page = PageHelper.startPage(pageNum, pageSize);
		orderMapper.query(customerId);

		// 架构师A的做法 start
//		for (Order order : page) {
//			User user = userMapper.find(order.getCustomerId());
//			if (user != null) {
//				order.setCustomerName(user.getUserName());
//			}
//		}
		// 架构师A的做法 end

		return page;
	}

}

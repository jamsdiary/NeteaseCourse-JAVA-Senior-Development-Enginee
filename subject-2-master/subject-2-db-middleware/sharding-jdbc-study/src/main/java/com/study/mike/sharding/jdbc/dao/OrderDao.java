package com.study.mike.sharding.jdbc.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.study.mike.sharding.jdbc.model.Order;

@Mapper
public interface OrderDao {

	@Insert("insert into t_order(order_time,customer_id) values(#{orderTime},#{customerId})")
	//@Insert("insert into t_order(order_id,order_time,customer_id) values(#{orderId},#{orderTime},#{customerId})")
	void addOrder(Order o);
	
	@Select("select order_id,order_time,customer_id from t_order where order_id=#{id}")
	Order get(@Param("id")Long orderId);
}

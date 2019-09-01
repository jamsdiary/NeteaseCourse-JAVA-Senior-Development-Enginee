package com.study.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.service.IOrderService;
import com.study.utils.RedisUtils;

/**
 * 订单号-通过redis的Incr实现
 * 
 * @author allen
 *
 */
@Service("redisOrderServiceImpl")
public class RedisOrderServiceImpl implements IOrderService {

	@Autowired
	private RedisUtils redis;

	/**
	 * 产生订单号
	 */
	@Override
	public void orderId() {
		String key = "system:order:id"; // key = 系统名：+ 模块：+ 功能 ：+ key 
		String prefix = getPrefix(new Date());
		long id = redis.getIncr(key, -1);
		// 加工
		System.out.println("insert into order_id(id) values('" + prefix + String.format("%1$05d", id) + "');");
	}
	
	/**
	 * 通过时间生成前缀 - 可根据业务自定定义
	 * @param date
	 * @return
	 */
	//2018-08-01
	private String getPrefix(Date date) {// 18 005 00 0005
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int day = c.get(Calendar.DAY_OF_YEAR);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		String dayFmt = String.format("%1$03d", day);
		String hourFmt = String.format("%1$02d", hour);
		return (year - 2000) + dayFmt + hourFmt;
	}
}

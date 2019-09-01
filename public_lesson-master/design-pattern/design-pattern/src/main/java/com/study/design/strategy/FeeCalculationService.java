package com.study.design.strategy;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

/** 订单收费计算服务 */
@Service
public class FeeCalculationService {
	/**
	 * 打折计算
	 * 
	 * @param type 用户类型
	 * @param cost 原价
	 * @return
	 */
	public double calculate(String type, double cost) {
		// vip 打9.5折，普通用户原价
		if ("vip".equals(type)) {
			return cost * 0.95;
		} else if ("normal".equals(type)) {
			return cost;
		} else {
			return cost;
		}
	}

	// 定义一个Map存储 打折策略 对象 (策略针对的类型(key） -> 对象本身(value))
	HashMap<String, DiscountStrategy> discountStrategyMap = new HashMap<>();

	public FeeCalculationService(List<DiscountStrategy> discountStrategies) {
		for (DiscountStrategy discountStrategy : discountStrategies) {
			discountStrategyMap.put(discountStrategy.type(), discountStrategy);
		}
	}
}

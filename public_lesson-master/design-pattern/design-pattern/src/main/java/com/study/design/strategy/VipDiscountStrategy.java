package com.study.design.strategy;

import org.springframework.stereotype.Service;

/** 针对VIP的打折策略 - 95折 */
@Service
public class VipDiscountStrategy implements DiscountStrategy{

	@Override
	public String type() {
		return "vip";
	}

	@Override
	public double discount(double cost) {
		return cost * 0.95;
	}

}

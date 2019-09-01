package com.study.design.strategy;

import org.springframework.stereotype.Service;

/** 针对normal的打折策略 - 95折 */
@Service
public class NormalDiscountStrategy implements DiscountStrategy {

	@Override
	public String type() {
		return "normal";
	}

	@Override
	public double discount(double cost) {
		return cost * 1;
	}

}

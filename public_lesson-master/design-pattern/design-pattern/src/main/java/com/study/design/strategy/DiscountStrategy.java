package com.study.design.strategy;

/** 打折策略 - 接口定义 */
public interface DiscountStrategy {
	/** 返回该策略针对的用户类型 */
	public String type() ; 
	
	/** 计算折后价格 */
	public double discount(double cost);
}

package com.study.design.strategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.study.design.TonyDesignPatternApplication;
import com.study.design.observer.spring.OrderService;

/** 策略模式 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TonyDesignPatternApplication.class)
public class SpringStrategyTest {
	@Autowired
	FeeCalculationService feeCalculationService;

	@Test
	public void test1() {
		double vipCost = feeCalculationService.calculate("vip", 100);
		System.out.println("vip客户，您此次消费100，折后价格： " + vipCost);
		
		double normalCost = feeCalculationService.calculate("normal", 100);
		System.out.println("normal客户，您此次消费100，折后价格： " + normalCost);
	}
}

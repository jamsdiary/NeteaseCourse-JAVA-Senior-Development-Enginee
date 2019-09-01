package com.study.design.observer.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.study.design.TonyDesignPatternApplication;
import com.study.design.observer.spring.OrderService;

/**
 * 
 * @author Tony
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TonyDesignPatternApplication.class)
public class SpringObserverTest {
	@Autowired
	OrderService orderService;

	@Test
	public void test1() {
		orderService.saveOrder();
	}
}

package com.study.design.observer.spring;

import org.springframework.context.ApplicationEvent;

/**
 * 订单创建事件
 * @author zhang
 *
 */
public class OrderEvent extends ApplicationEvent{

	private static final long serialVersionUID = 1L;

	public OrderEvent(Object source) {
		super(source);
	}
	
}

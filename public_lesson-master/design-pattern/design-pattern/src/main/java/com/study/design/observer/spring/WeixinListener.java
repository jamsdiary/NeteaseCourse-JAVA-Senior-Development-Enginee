package com.study.design.observer.spring;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 监听订单创建事件 - 发送微信
 * 
 * @author zhang
 *
 */
@Component
public class WeixinListener implements SmartApplicationListener {

	// 当事件发生的时候，执行
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		System.out.println("收到订单事件， 执行发送##微信#的操作");
	}

	// 定义多个监听者的执行顺序
	@Override
	public int getOrder() {
		// 值越大，顺序越往后
		return 59;
	}

	// 监听哪一种事件
	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
		return eventType == OrderEvent.class;
	}

	@Override
	public boolean supportsSourceType(Class<?> sourceType) {
		return true;
	}

}

package com.study.transaction.dispatch.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.transaction.dispatch.service.DispatchService;

/**
 * 运单系统http API
 * 
 * @author Tony
 *
 */
@RestController
@RequestMapping("/dispatch-api")
public class DispatchController {

	@Autowired
	private DispatchService dispatchService;

	// 下订单后，添加调度信息
	@GetMapping("/dispatch")
	public String lock(String orderId) throws Exception {
		Thread.sleep(3000L); // 此处模拟业务耗时，接口调用者会认为超时
		dispatchService.dispatch(orderId); // 将外卖订单分配给送餐小哥
		return "ok";
	}

}

package com.study.session.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示负载均衡
 * 
 * @author allen
 */
@Controller
public class LoadBalanceTestController {

	@RequestMapping("loadBalanceTest")
	public String test() {
		return "test";
	}
}

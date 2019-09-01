package com.study.rabbitmq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.rabbitmq.model.User;
import com.study.rabbitmq.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User getUser(@PathVariable("id") String id) {
		User user = userService.getUser(id);
		return user;
		// 压测： jmeter , loadruner, ab, 自己写代码实现（模拟多线程并发）
	}

}

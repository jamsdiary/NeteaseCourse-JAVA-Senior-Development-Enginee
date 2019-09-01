package com.study.rabbitmq.service.impl;

import com.study.rabbitmq.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.rabbitmq.mapper.UserMapper;
import com.study.rabbitmq.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
 
	@Autowired
    UserMapper userMapper;
	
	public User getUser(String id) {
		return userMapper.selectUserByID(id);
	}
 
}

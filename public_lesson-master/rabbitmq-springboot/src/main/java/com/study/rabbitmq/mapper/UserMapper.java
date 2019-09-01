package com.study.rabbitmq.mapper;

import com.study.rabbitmq.model.User;

public interface UserMapper {

	public User selectUserByID(String id);

}

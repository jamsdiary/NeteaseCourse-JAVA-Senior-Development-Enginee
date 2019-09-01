package com.study.mycat.mapper;

import java.util.List;

import com.study.mycat.model.UserInfo;

public interface UserInfoMapper {

	public List<UserInfo> selectAll();

	public UserInfo selectByPrimaryKey(Integer id);

	public void deleteByPrimaryKey(Integer id);

	public void updateByPrimaryKey(UserInfo userInfo);

	public void insert(UserInfo userInfo);
}

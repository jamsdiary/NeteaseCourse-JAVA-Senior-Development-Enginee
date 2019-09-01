package com.study.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.mybatis.entity.User;
import com.study.mybatis.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 通过subList方法，获取到两个索引间的所有数据
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<User> selectUsersByArray(int pageNo, int pageSize) {
		List<User> students = userMapper.selectUsersByArray();
		// 从第几条数据开始
		int firstIndex = (pageNo - 1) * pageSize;
		// 到第几条数据结束
		int lastIndex = pageNo * pageSize;
		return students.subList(firstIndex, lastIndex);
	}

	/**
	 * 在sql语句后面添加limit实现分页
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public List<User> selectUsersBySql(int pageNo, int pageSize) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("currIndex", (pageNo - 1) * pageSize);
		data.put("pageSize", pageSize);
		return userMapper.selectUsersBySql(data);
	}

	/**
	 * 通过RowBounds参数进行分页
	 * 
	 * @param userName
	 * @param rowBounds
	 * @return
	 */
	//@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.SUPPORTS)
	public List<User> selectUsersByRowBounds(int start, int limit) {
		return userMapper.selectUsersByRowBounds(new RowBounds(start, limit));
	}
	
	/**
	 * 通过自定义插件分页
	 * 
	 * @param map
	 * @return
	 */
	public List<User> selectUsersByPage(Map<String, Object> map) {
		return userMapper.selectUsersByPage(map);
	}

}

package com.study.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.study.mybatis.entity.User;

/**
 * 数据访问层接口
 * 
 * @author allen
 */
public interface UserMapper {
	
	// 借助数组进行分页
	public List<User> selectUsersByArray();
	
	// 借助Sql语句进行分页
	public List<User> selectUsersBySql(Map<String,Object> data);
	
	// RowBounds实现分页
	public List<User> selectUsersByRowBounds(RowBounds rowBounds);
	
	// 插件式分页
	public List<User> selectUsersByPage(Map<String, Object> map);
	
	//public List<Article> selectArticleListPage(@Param("page") PageInfo page, @Param("userid") int userid);
}

package com.study.annotation.mapper;

import java.util.List;

import com.study.annotation.model.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
@CacheNamespace
public interface UserMapper {

	@Insert("insert t_user(id,user_name) values(#{id},#{userName})")
	void insert(User u);

	@Update("update t_user set user_name = #{userName} where id=#{id} ")
	void update(User u);

	@Delete("delete from t_user where id=#{id} ")
	void delete(@Param("id") String id);

	@Select("select id,user_name from t_user where id=#{id} ")
	User find(@Param("id") String id);

	List<User> query(@Param("userName") String userName);

}

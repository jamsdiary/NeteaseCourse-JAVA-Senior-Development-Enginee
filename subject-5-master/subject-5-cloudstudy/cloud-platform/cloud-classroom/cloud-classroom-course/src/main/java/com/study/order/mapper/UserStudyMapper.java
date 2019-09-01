package com.study.order.mapper;

import com.study.order.bean.UserStudy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserStudyMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserStudy record);

    int insertSelective(UserStudy record);

    UserStudy selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserStudy record);

    int updateByPrimaryKey(UserStudy record);

    List<UserStudy> getAllUserStudy();

    List<UserStudy> getAllUserStudiesOfCurrentUser(int userId);

    List<UserStudy> getUserStudiesByPage(@Param("start") int start, @Param("size") Integer size, @Param("key") String key);

    Long getCountByKey(@Param("key") String key);
}
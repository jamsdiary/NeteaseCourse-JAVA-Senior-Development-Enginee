package com.study.order.mapper;

import com.study.order.bean.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseMapper {

    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    List<Course> getAllCourse();


    List<Course> getAllCourseOfTeacher(String userId);

    List<Course> getCoursesByPage(@Param("start") int start, @Param("size") Integer size, @Param("key") String key);

    Long getCountByKey(@Param("key") String key);
}
package com.study.order.mapper;

import com.study.order.bean.LessonSchedule;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LessonScheduleMapper {
    int deleteByPrimaryKey(String id);

    int insert(LessonSchedule record);

    int insertSelective(LessonSchedule record);

    LessonSchedule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LessonSchedule record);

    int updateByPrimaryKey(LessonSchedule record);


    List<LessonSchedule> getAllLessonSchedule();

    List<LessonSchedule> getLessonSchedulesByCourseId(String courseId);

    List<LessonSchedule> getLessonSchedulesByPage(@Param("start") int start, @Param("size") Integer size, @Param("key") String key);

    Long getCountByKey(@Param("key") String key);
}
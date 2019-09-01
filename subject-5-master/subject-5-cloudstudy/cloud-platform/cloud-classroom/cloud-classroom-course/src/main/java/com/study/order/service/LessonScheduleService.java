package com.study.order.service;

import com.study.order.bean.LessonSchedule;
import com.study.order.mapper.LessonScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andy on 2018/1/24.
 */
@Service
public class LessonScheduleService {
    @Autowired
    LessonScheduleMapper lessonScheduleMapper;
    public int addLessonSchedule(LessonSchedule LessonSchedule) {
        return lessonScheduleMapper.insert(LessonSchedule);
    }

    public List<LessonSchedule> getAllLessonSchedule() {
        return lessonScheduleMapper.getAllLessonSchedule();
    }

    public List<LessonSchedule> getLessonSchedulesByPage(Integer page, Integer size,String name) {
        int start = (page-1)*size;
        return lessonScheduleMapper.getLessonSchedulesByPage(start,size,name);
    }


    public Long getCountByKey(String name) {
        return lessonScheduleMapper.getCountByKey(name);
    }

    public LessonSchedule getLessonSchedule(String id) {
        return lessonScheduleMapper.selectByPrimaryKey(id);
    }


    public int updateLessonSchedule(LessonSchedule LessonSchedule) {
        return lessonScheduleMapper.updateByPrimaryKeySelective(LessonSchedule);
    }

    public int deleteLessonSchedule(String id) {
        return lessonScheduleMapper.deleteByPrimaryKey(id);
    }

}

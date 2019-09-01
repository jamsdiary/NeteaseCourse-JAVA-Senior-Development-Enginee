package com.study.order.controller;

import com.study.order.bean.LessonSchedule;
import com.study.order.bean.RespBean;
import com.study.order.service.LessonScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 课程
 */
@RestController
@RequestMapping("/cloud/classroom/lessonSchedule")
public class LessonScheduleController {
    @Autowired
    LessonScheduleService lessonScheduleService;

    @RequestMapping(method = RequestMethod.POST)
    public RespBean addLessonSchedule(LessonSchedule lessonSchedule) {
        //User user = UserUtils.getCurrentUser();
        if (lessonScheduleService.addLessonSchedule(lessonSchedule) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public LessonSchedule getLessonSchedule(@PathVariable String id) {
        return lessonScheduleService.getLessonSchedule(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<LessonSchedule> getAllLessonSchedules() {
        List<LessonSchedule> LessonSchedules = lessonScheduleService.getAllLessonSchedule();
        return LessonSchedules;
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    public Map<String, Object> getLessonSchedulesByPage(@RequestParam(defaultValue = "1") Integer page,
                                                        @RequestParam(defaultValue = "10") Integer size, String name) {
        name = Optional.ofNullable(name).orElse("");
        Long count = lessonScheduleService.getCountByKey(name);
        List<LessonSchedule> LessonSchedules = null;
        LessonSchedules = lessonScheduleService.getLessonSchedulesByPage(page, size, name);
        Map<String, Object> map = new HashMap<>();
        map.put("LessonSchedules", LessonSchedules);
        map.put("count", count);
        return map;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RespBean updateLessonSchedule(LessonSchedule LessonSchedule) {
        if (lessonScheduleService.updateLessonSchedule(LessonSchedule) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RespBean deleteLessonSchedule(@PathVariable String id) {
        if (lessonScheduleService.deleteLessonSchedule(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }


}

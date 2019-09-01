package com.study.order.controller;

import com.study.order.bean.Course;
import com.study.order.bean.RespBean;
import com.study.order.bean.UserStudy;
import com.study.order.common.JwtUtil;
import com.study.order.service.CourseService;
import com.study.order.service.UserStudyService;
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
@RequestMapping("/cloud/classroom/user_study")
public class UserStudyController {
    @Autowired
    UserStudyService userStudyService;

    @Autowired
    CourseService courseService;
    @RequestMapping(method = RequestMethod.POST)
    public RespBean addUserStudy(@RequestBody UserStudy userStudy) {
        String userId = JwtUtil.getUserId();
        if (userStudyService.addUserStudy(userStudy) == 1) {
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public UserStudy getUserStudy(@PathVariable String id) {
        return userStudyService.getUserStudy(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserStudy> getAllUserStudies() {
        List<UserStudy> UserStudies = userStudyService.getAllUserStudy();
        return UserStudies;
    }


    @RequestMapping(value = "/page/list", method = RequestMethod.GET)
    public Map<String, Object> getUserStudiesByPage(            @RequestParam(defaultValue = "1") Integer page,
                                                       @RequestParam(defaultValue = "10") Integer size, String name)
    {
        name = Optional.ofNullable(name).orElse("");
        Long count = userStudyService.getCountByKey(name);
        List<UserStudy> UserStudies = null;
        UserStudies = userStudyService.getUserStudiesByPage(page,size,name);
        Map<String, Object> map = new HashMap<>();
        map.put("UserStudies", UserStudies);
        map.put("count", count);
        return map;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RespBean updateUserStudy(UserStudy UserStudy) {
        if (userStudyService.updateUserStudy(UserStudy) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public RespBean deleteUserStudy(@PathVariable String id) {
        if (userStudyService.deleteUserStudy(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }


    @RequestMapping(value = "/current/user/list", method = RequestMethod.GET)
    public List<UserStudy> getAllUserStudiesOfCurrentUser() {
        List<UserStudy> UserStudies = userStudyService.getAllUserStudiesOfCurrentUser();
        UserStudies.stream().forEach(item->{
            Course course =  courseService.getCourse(item.getCourseId());
            if(course != null){
                item.setCourseName(course.getName());
            }
        });
        return UserStudies;
    }

}

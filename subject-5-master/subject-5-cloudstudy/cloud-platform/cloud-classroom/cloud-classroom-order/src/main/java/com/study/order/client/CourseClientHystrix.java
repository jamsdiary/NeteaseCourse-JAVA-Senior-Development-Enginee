package com.study.order.client;


import com.study.order.bean.Course;
import com.study.order.bean.RespBean;
import com.study.order.bean.UserStudy;
import com.study.order.client.CourseClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhongxin on 2017/9/17.
 */
@Component
public class CourseClientHystrix implements CourseClient {
    @Override
    public Course getCourse(String courseId) {
        return null;
    }

    @Override
    public RespBean addUserStudy(UserStudy userStudy) {
        return null;
    }

    @Override
    public Map<String, String> getCourseNames() {
        return null;
    }


}

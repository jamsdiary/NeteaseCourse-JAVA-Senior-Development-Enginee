package com.study.thread.future;

import com.study.thread.future.service.UserServiceForkJoin;
import com.study.thread.future.service.UserServiceFutureTask;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.study.thread.future.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebsiteDemoApplication.class)
public class WebsiteDemoApplicationTests {

    @Before
    public void start() {
        System.out.println("开始测试");
    }

    @After
    public void end() {
        System.out.println("结束测试");
    }

    @Test
    public void ex() {
        int x = 0, y = 100;
        System.out.println("结果" + (x + y));
        int i = 1 / 0;
    }

    @Autowired
    UserService userService;

    @Autowired
    UserServiceForkJoin userServiceForkJoin;
    @Autowired
    UserServiceFutureTask userServiceFutureTask;

    @Test
    public void testUserSerivce() throws Exception {
        // 调用
        long currentTimeMillis = System.currentTimeMillis();
        // http 实际就是 线程 调用service
        Object userInfo = userServiceFutureTask.getUserInfo("tony");

        System.out.println("getUserInfo总执行时间为" + (System.currentTimeMillis() - currentTimeMillis));
        System.out.println(userInfo.toString());
    }

}

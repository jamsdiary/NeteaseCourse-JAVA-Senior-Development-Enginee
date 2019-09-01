package com.study.cache.redis.a0_example;

import com.study.cache.redis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single") // 设置profile
public class SpringCacheTests {

    @Autowired
    SpringCacheService springCacheService;

    // ---------------spring cache注解演示
    // get
    @Test
    public void springCacheTest() throws Exception {
        User user = springCacheService.findUserById("tony");
        System.out.println(user);
    }

    // update
    @Test
    public void springCacheTest2() throws Exception {
        springCacheService.updateUser(new User("hhhhhhh-2", "tony"));
        User user = springCacheService.findUserById("tony");
        System.out.println(user);
    }

    // delete
    @Test
    public void springCacheTest3() throws Exception {
        springCacheService.deleteUserById("tony");
    }
}

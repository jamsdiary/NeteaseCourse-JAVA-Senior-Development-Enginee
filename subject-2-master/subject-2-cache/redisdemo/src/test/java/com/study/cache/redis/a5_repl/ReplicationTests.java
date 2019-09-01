package com.study.cache.redis.a5_repl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("replication") // 激活主从复制的配置
public class ReplicationTests {
    @Autowired
    ReplicationExampleService replicationExampleService;

    @Test
    public void setTest() {
        replicationExampleService.setByCache("tony", "hahhhhh");
    }
}

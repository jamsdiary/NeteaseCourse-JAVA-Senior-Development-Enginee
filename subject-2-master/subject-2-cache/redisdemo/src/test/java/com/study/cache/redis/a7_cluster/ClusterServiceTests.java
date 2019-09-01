package com.study.cache.redis.a7_cluster;

import com.study.cache.redis.a0_example.SingleExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("a7_cluster") // 设置profile
// 集群对于客户端而言，基本是无感知的
public class ClusterServiceTests {
    @Autowired
    ClusterService clusterService;

    @Test
    public void setTest() {
        clusterService.set("tony", "hahhhhh");
        clusterService.set("a", "1");
        clusterService.set("foo", "bar");
    }

    // 测试cluster集群故障时的反应
    @Test
    public void failoverTest() {
        while (true) {
            try {
                long i = System.currentTimeMillis();
                clusterService.set("tony", i + "");
                // delay 10ms
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

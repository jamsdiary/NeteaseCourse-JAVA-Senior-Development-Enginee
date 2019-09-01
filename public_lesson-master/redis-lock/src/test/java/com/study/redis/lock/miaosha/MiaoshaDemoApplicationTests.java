package com.study.redis.lock.miaosha;

import com.study.redis.lock.miaosha.service.MiaoshaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MiaoshaDemoApplicationTests {

    long timed = 0L;

    @Before
    public void start() {
        System.out.println("开始测试");
    }

    @After
    public void end() {
        System.out.println("结束测试,执行时长：" + (System.currentTimeMillis() - timed));
    }

    @Autowired
    MiaoshaService miaoshaService;

    // 循环创建 N个线程，模拟用户请求
    @Test
    public void benchmark() throws InterruptedException {
        // 模拟的请求数量
        final int threadNum = 500;
        // 倒计数器，用于模拟高并发（信号枪机制）
        CountDownLatch cdl = new CountDownLatch(threadNum);
        // 循环创建N个线程
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            String userId = "Tony";
            Thread thread = new Thread(() -> {
                try {
                    // 等待cdl值为0，也就是其他线程就绪后，再运行后续的代码
                    cdl.await();
                    // http请求实际上就是多线程调用这个方法
                    miaoshaService.miaosha("bike", userId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            threads[i] = thread;
            thread.start();
            // 倒计时器 减一
            cdl.countDown();

        }

        // 等待上面所有线程执行完毕之后，结束测试
        for (Thread thread : threads) {
            thread.join();
        }
    }

}

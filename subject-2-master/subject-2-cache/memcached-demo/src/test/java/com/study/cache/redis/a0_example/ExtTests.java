package com.study.cache.redis.a0_example;

import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ExtTests {

    @Test
    public void extTest() throws IOException, InterruptedException, MemcachedException, TimeoutException {
        for (int j = 0; j < 1; j++) {
            XMemcachedClient xMemcachedClient = new XMemcachedClient("192.168.100.243", 11211);
            new Thread(() -> {
                String content = "";
                for (int i = 0; i < 10240; i++) {
                    content += String.valueOf(i);
                }
                for (int i = 0; i < 1024 * 64; i++) {
                    try {
                        xMemcachedClient.set(i + "", 36000, content);
                        System.out.println("发送成功");
                    } catch (TimeoutException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (MemcachedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.in.read();
    }

    @Test
    public void justTest() {
        System.out.println(Math.abs("uid_10001".hashCode()) % 3);
        System.out.println(Math.abs("uid_10002".hashCode()) % 3);
        System.out.println(Math.abs("uid_10003".hashCode()) % 3);
    }
}

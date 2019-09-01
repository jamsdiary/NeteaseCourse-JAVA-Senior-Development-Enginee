package com.study.cache.redis.a1_cluster;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("cluster") // 设置profile
public class ClientClusterTests {

    @Autowired
    UserServiceByCustom userServiceByCustom;

    @Test
    public void customClientClusterTest() throws Exception {
        userServiceByCustom.findUser("tony");
        userServiceByCustom.findUser("mengmeng");
        userServiceByCustom.findUser("jack");
        userServiceByCustom.findUser("tony2");
        userServiceByCustom.findUser("mengmeng2");
        userServiceByCustom.findUser("jack2");
    }


    @Autowired
    UserService userService;

    @Test
    public void xmemcachedclientClusterTest() throws Exception {
        // 每次都向上面这么写一段获取client的代码，太麻烦，memcacheclient已经内置了这样的功能
        // 在调用set和其他方法时，自动帮我们进行选择
        userService.findUser("tony33");
        userService.findUser("mengmeng33");
        userService.findUser("jack33");
        userService.findUser("tony44");
        userService.findUser("mengmeng44");
        userService.findUser("jack44");

    }

    // 使用推特中间件代码memcached
    @Test
    public void twemproxyClusterTest() throws IOException, InterruptedException, MemcachedException, TimeoutException {
        // 和代理直接连接即可，客户端无感知
        XMemcachedClient xMemcachedClient = new XMemcachedClient("192.168.100.243", 22222);
        xMemcachedClient.set("uid10001", 0, "{uname:tony,age:18}");
        xMemcachedClient.set("uid10003", 0, "{uname:jack,age:17}");
        xMemcachedClient.set("uid10002", 0, "{uname:mengmeng,age:19}");
    }
}

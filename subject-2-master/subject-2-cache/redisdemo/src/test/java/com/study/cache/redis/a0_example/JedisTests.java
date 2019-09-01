package com.study.cache.redis.a0_example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("single") // 设置profile
public class JedisTests {

    // ------------------------ jedis 工具直连演示
    // jedis和redis命令名称匹配度最高，最为简洁，学习难度最低

    // 列表~ 集合数据存储~ java.util.List，java.util.Stack
    // 生产者消费者（简单MQ）
    @Test
    public void list() {
        Jedis jedis = new Jedis("192.168.100.241", 6379);
        // 插入数据1 --- 2 --- 3
        jedis.rpush("queue_1", "1");
        jedis.rpush("queue_1", "2", "3");

        List<String> strings = jedis.lrange("queue_1", 0, -1);
        for (String string : strings) {
            System.out.println(string);
        }

        // 消费者线程简例
        while (true) {
            String item = jedis.lpop("queue_1");
            if (item == null) break;
            System.out.println(item);
        }

        jedis.close();
    }

    // 类似：在redis里面存储一个hashmap
    // 推荐的方式，无特殊需求是，一般的缓存都用这个
    @Test
    public void hashTest() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", "tony");
        user.put("age", 18);
        user.put("userId", 10001);
        System.out.println(user);

        Jedis jedis = new Jedis("192.168.100.241", 6379);
        jedis.hset("user_10001", "name", "tony");
        jedis.hset("user_10001", "age", "18");
        jedis.hset("user_10001", "userId", "10001");
        System.out.println("redis版本~~~~~");
        // jedis.hget("user_10001", "name");
        System.out.println(jedis.hgetAll("user_10001"));
        jedis.close();
    }

    // 用set实现（交集 并集）
    // 交集示例： 共同关注的好友
    // 并集示例：
    @Test
    public void setTest() {
        // 取出两个人共同关注的好友
        Jedis jedis = new Jedis("192.168.100.241", 6379);
        // 每个人维护一个set
        jedis.sadd("user_A", "userC", "userD", "userE");
        jedis.sadd("user_B", "userC", "userE", "userF");
        // 取出共同关注
        Set<String> sinter = jedis.sinter("user_A", "user_B");
        System.out.println(sinter);

        // 检索给某一个帖子点赞/转发的
        jedis.sadd("trs_tp_1001", "userC", "userD", "userE");
        jedis.sadd("star_tp_1001", "userE", "userF");
        // 取出共同人群
        Set<String> union = jedis.sunion("star_tp_1001", "trs_tp_1001");
        System.out.println(union);

        jedis.close();
    }

    // 游戏排行榜
    @Test
    public void zsetTest() {
        Jedis jedis = new Jedis("192.168.100.241", 6379);
        String ranksKeyName = "exam_rank";
        jedis.zadd(ranksKeyName, 100.0, "tony");
        jedis.zadd(ranksKeyName, 82.0, "allen");
        jedis.zadd(ranksKeyName, 90, "mengmeng");
        jedis.zadd(ranksKeyName, 96, "netease");
        jedis.zadd(ranksKeyName, 89, "ali");

        Set<String> stringSet = jedis.zrevrange(ranksKeyName, 0, 2);
        System.out.println("返回前三名:");
        for (String s : stringSet) {
            System.out.println(s);
        }

        Long zcount = jedis.zcount(ranksKeyName, 85, 100);
        System.out.println("超过85分的数量 " + zcount);

        jedis.close();
    }
}


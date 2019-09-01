package com.study.cache.java;

public class MapCacheDemoTests {
    public static void main(String[] args) throws InterruptedException {
        MapCacheDemo mapCacheDemo = new MapCacheDemo();
        mapCacheDemo.add("uid_10001", "{1}", 5 * 1000);
        mapCacheDemo.add("uid_10002", "{2}", 5 * 1000);
        mapCacheDemo.add("uid_10003", "{3}", 5 * 1000);
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));
        Thread.sleep(5000L);
        System.out.println("5秒钟过后");
        System.out.println("从缓存中取出值:" + mapCacheDemo.get("uid_10001"));
        // 5秒后数据自动清除了~
    }
}

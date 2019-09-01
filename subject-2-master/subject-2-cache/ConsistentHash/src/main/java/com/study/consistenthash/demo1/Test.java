package com.study.consistenthash.demo1;

import java.util.HashSet;

/**
 * @Auther: allen
 * @Date: 2019/3/14 17:43
 */
public class Test {
    public static void main(String[] args) {

        HashSet<ServerInfo> set = new HashSet<ServerInfo>();
        set.add(new ServerInfo("192.168.1.1","9001"));
        set.add(new ServerInfo("192.168.1.2","9002"));
        set.add(new ServerInfo("192.168.1.3","9003"));
        set.add(new ServerInfo("192.168.1.4","9004"));

        ConsistentHash<ServerInfo> consistentHash = new ConsistentHash<ServerInfo>(new ConsistentHash.HashFunction(), 1000, set);

        System.out.println(consistentHash.get("192.168.1.4").getIp());
    }
}

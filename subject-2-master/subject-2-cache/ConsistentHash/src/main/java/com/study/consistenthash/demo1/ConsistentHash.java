package com.study.consistenthash.demo1;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * 一致性hash的java实现。从memcache提取
 *
 * @Auther: allen
 * @Date: 2019/3/14 17:40
 */
public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Integer, ServerInfo> circle = new TreeMap<Integer, ServerInfo>();

    public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,
                          Collection<ServerInfo> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (ServerInfo node : nodes) {
            add(node);
        }
    }

    public void add(ServerInfo node) {
        circle.put(hashFunction.hash(node.getIp()), node);
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.getIp() + i), node);
        }
    }

    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    public ServerInfo get(Object key) {
        if (circle.isEmpty()) {
            return null;
        }
        int hash = hashFunction.hash(key);
        if (!circle.containsKey(hash)) {
            SortedMap<Integer, ServerInfo> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }

    static class HashFunction {
        int hash(Object key) {
            // md5加密后，hashcode
            return MD5.md5(key.toString()).hashCode();
        }
    }
}

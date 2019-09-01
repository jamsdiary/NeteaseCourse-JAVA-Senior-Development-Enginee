package com.study.cache.redis.a1_cluster;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 客户端集群工具类
 */
public class ClusterClientFactory {

    /**
     * 根据key选择客户端
     */
    public static MemcachedClient getClient(String key) throws IOException {
        ArrayList<XMemcachedClient> servers = new ArrayList<>();
        servers.add(new XMemcachedClient("192.168.100.243", 11211));
        servers.add(new XMemcachedClient("192.168.100.243", 11212));
        servers.add(new XMemcachedClient("192.168.100.243", 11213));

        // 计算key的hash值
        int hashCode = Math.abs(key.hashCode());
        // 计算对应的位置(直接和服务器数量取模)
        int slot = hashCode % servers.size();

        return servers.get(slot);
    }
}

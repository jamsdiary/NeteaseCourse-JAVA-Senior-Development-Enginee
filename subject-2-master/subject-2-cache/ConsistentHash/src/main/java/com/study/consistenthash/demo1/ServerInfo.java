package com.study.consistenthash.demo1;

/**
 * @Auther: allen
 * @Date: 2019/3/14 17:53
 */
public class ServerInfo {
    private String ip;// IP
    private String name;// 名称

    public ServerInfo(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 复写toString方法，使用节点IP当做hash的KEY
     */
    @Override
    public String toString() {
        return ip;
    }
}

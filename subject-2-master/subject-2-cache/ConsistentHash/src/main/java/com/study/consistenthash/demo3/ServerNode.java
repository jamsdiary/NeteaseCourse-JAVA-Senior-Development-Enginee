package com.study.consistenthash.demo3;

/**
 * @Auther: allen
 * @Date: 2019/3/14 18:07
 */
public class ServerNode {
    private String serverNodeName;
    private long  serverNodeHash;

    public ServerNode(String serverNodeName, long serverNodeHash) {
        super();
        this.serverNodeName = serverNodeName;
        this.serverNodeHash = serverNodeHash;
    }
    public String getServerNodeName() {
        return serverNodeName;
    }
    public void setServerNodeName(String serverNodeName) {
        this.serverNodeName = serverNodeName;
    }
    public long getServerNodeHash() {
        return serverNodeHash;
    }
    public void setServerNodeHash(long serverNodeHash) {
        this.serverNodeHash = serverNodeHash;
    }
}

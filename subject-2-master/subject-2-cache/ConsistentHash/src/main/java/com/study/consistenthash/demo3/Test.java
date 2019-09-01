package com.study.consistenthash.demo3;

/**
 * @Auther: allen
 * @Date: 2019/3/14 18:09
 */
public class Test {
    public static void main(String[] args){
        ConsistentHashWithoutVirtualNode ch = new ConsistentHashWithoutVirtualNode();
        //添加一系列的服务器节点
        String[] servers = {
                "192.168.0.0:111",
                "192.168.0.1:111",
                "192.168.0.2:111",
                "192.168.0.3:111",
                "192.168.0.4:111"};
        for(String server:servers){
            ch.addServerNode(server);
        }
        //打印输出一下服务器节点
        ch.printServerNodes();

        //看看下面的客户端节点会被路由到哪个服务器节点
        String[] nodes = {
                "127.0.0.1:1111",
                "221.226.0.1:2222",
                "10.211.0.1:3333"};
        System.out.println("此时，各个客户端的路由情况如下：");
        for(String node:nodes){
            ServerNode serverNode = ch.getServerNode(node);
            System.out.println(node+","+ ch.getHash(node)+"------->"+
                    serverNode.getServerNodeName()+","+serverNode.getServerNodeHash());
        }

        //如果由一个服务器节点宕机，即需要将这个节点从服务器集群中移除
        ch.deleteServerNode("192.168.0.0:111");

        System.out.println("删除节点后，再看看同样的客户端的路由情况，如下：");
        for(String node:nodes){
            ServerNode serverNode = ch.getServerNode(node);
            System.out.println(node+","+ ch.getHash(node)+"------->"+
                    serverNode.getServerNodeName()+","+serverNode.getServerNodeHash());
        }


    }
}

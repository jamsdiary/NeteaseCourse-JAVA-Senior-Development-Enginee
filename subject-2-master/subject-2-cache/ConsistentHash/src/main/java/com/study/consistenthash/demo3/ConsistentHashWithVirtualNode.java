package com.study.consistenthash.demo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.CRC32;

/**
 * @Auther: allen
 * @Date: 2019/3/14 18:10
 */
public class ConsistentHashWithVirtualNode {
    //保存虚拟服务器节点节点
    List<VirtualServerNode> virtualServerNodes = new ArrayList<VirtualServerNode>();
    //每个物理节点对应的虚拟节点的个数
    private final static int VIRTUAL_NUM = 5;
    //添加服务器节点
    public void addServerNode(String serverName){
        if(serverName==null){
            return;
        }
        for(int i=0;i<VIRTUAL_NUM;i++){
            //这里假设，虚拟节点的名字为类似这样的形式：serverName+"&&VN"+i，这样方便从虚拟节点得到物理节点
            String virtualServerNodeName = serverName+"&&VN"+i;
            long hash = getHash(virtualServerNodeName);
            VirtualServerNode vsNode = new VirtualServerNode(serverName, hash);
            virtualServerNodes.add(vsNode);
        }
        //将virtualServerNodes进行排序
        Collections.sort(virtualServerNodes,new Comparator<VirtualServerNode>() {

            @Override
            public int compare(VirtualServerNode node1, VirtualServerNode node2) {
                if(node1.getVirtualServerNodeHash()<node2.getVirtualServerNodeHash()){
                    return -1;
                }
                return 1;
            }

        });

    }
    public long getHash(String serverNodeName) {
        CRC32 crc32 = new CRC32();
        crc32.update(serverNodeName.getBytes());
        return crc32.getValue();
    }
    //删除服务器节点,即要删除其物理服务器节点对应的所有虚拟节点
    public void deleteServerNode(String serverName){

        for(int i=0;i<virtualServerNodes.size();i++){
            VirtualServerNode node = virtualServerNodes.get(i);

            if(node.getServerNodeName().contains(serverName)){//这里用了contain查找，这里就把该物理服务器节点对应的虚拟节点都删除了
                virtualServerNodes.remove(node);
                    /*
                     * 删除元素后，需要把下标减一。这是因为在每次删除元素后，ArrayList会将后面部分的元素依次往上挪一个位置(就是copy)，
                     * 所以，下一个需要访问的下标还是当前下标，所以必须得减一才能把所有元素都遍历完。
                     * */
                i--;
            }
        }
    }
    //得到应当路由到的结点
    public VirtualServerNode getServerNode(String key){
        //得到key的hash值
        long hash = getHash(key);
        //在VirtualServerNode中找到大于hash且离其最近的的那个VirtualServerNode
        //由于serverNodes是升序排列的，因此，找到的第一个大于hash的就是目标节点
        for(VirtualServerNode node:virtualServerNodes){
            if(node.getVirtualServerNodeHash()>hash){
                return node;
            }
        }
        //如果没有找到，则说明此key的hash值比所有服务器节点的hash值都大，因此返回最小hash值的那个Server节点
        return virtualServerNodes.get(0);

    }

    public void printServerNodes(){
        System.out.println("所有的服务器节点信息如下：");
        for(VirtualServerNode node:virtualServerNodes){
            System.out.println(node.getServerNodeName()+":"+node.getVirtualServerNodeHash());
        }
    }

    public static void main(String[] args){
        ConsistentHashWithVirtualNode ch = new ConsistentHashWithVirtualNode();
        //添加一系列的服务器节点
        String[] servers = {"192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111",
                "192.168.0.3:111", "192.168.0.4:111"};
        for(String server:servers){
            ch.addServerNode(server);
        }
        //打印输出一下服务器节点
        ch.printServerNodes();

        //看看下面的客户端节点会被路由到哪个服务器节点
        String[] nodes = {"127.0.0.1:1111", "221.226.0.1:2222", "10.211.0.1:3333"};
        System.out.println("此时，各个客户端的路由情况如下：");
        for(String node:nodes){
            VirtualServerNode virtualServerNode = ch.getServerNode(node);
            System.out.println(node+","+ ch.getHash(node)+"------->"+
                    virtualServerNode.getServerNodeName()+","+virtualServerNode.getVirtualServerNodeHash());
        }

        //如果由一个服务器节点宕机，即需要将这个节点从服务器集群中移除
        String deleteNodeName="192.168.0.2:111";
        ch.deleteServerNode(deleteNodeName);

        System.out.println("删除节点"+deleteNodeName+"后，再看看同样的客户端的路由情况，如下：");
        for(String node:nodes){
            VirtualServerNode virtualServerNode = ch.getServerNode(node);
            System.out.println(node+","+ ch.getHash(node)+"------->"+
                    virtualServerNode.getServerNodeName()+","+virtualServerNode.getVirtualServerNodeHash());
        }


    }
}

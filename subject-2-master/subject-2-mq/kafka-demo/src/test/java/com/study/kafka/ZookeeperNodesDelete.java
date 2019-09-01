package com.study.kafka;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: allen
 * @Date: 2019/4/20 20:11
 */
public class ZookeeperNodesDelete {
    /**
     * 递归删除zookeeper非空节点
     *
     * @param zk
     * @param nodeStr
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static void deleteSubNode(ZooKeeper zk, String nodeStr) throws IOException, KeeperException, InterruptedException {
        String nodePath = nodeStr;
        //打印当前节点路径
        System.out.println("Node Path >>>>>>>>> [" + nodePath + " ]");
        if (zk.getChildren(nodePath, false).size() == 0) {
            //删除节点
            System.out.println("Deleting Node Path >>>>>>>>> [" + nodePath + " ]");
            //  zk.delete(nodePath,-1);
        } else {
            //递归查找非空子节点
            List<String> list = zk.getChildren(nodeStr, true);
            for (String str : list) {
                deleteSubNode(zk, nodePath + "/" + str);
            }
        }
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String parentNodePath = "/";
        ZooKeeper zk = new ZooKeeper("192.168.100.249:" + 2181, 30000,
                new Watcher() {
                    // 监控所有被触发的事件
                    public void process(WatchedEvent event) {
                        System.out.println("已经触发了" + event.getType() + "事件！");
                    }
                });

        //递归删除节点所有子节点
        deleteSubNode(zk, parentNodePath);
        // 关闭zk连接
        zk.close();
    }

}

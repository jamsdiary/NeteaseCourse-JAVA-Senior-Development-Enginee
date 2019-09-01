package com.study.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Auther: allen
 * @Date: 2019/3/14 19:28
 */
public class NodeArray_new {
    /** 按照 键 排序*/
    TreeMap<Integer, Node> nodes = new TreeMap<>();

    void addNode(Node node) {
        nodes.put(node.hashCode(), node);
    }

    void put(Obj obj) {
        int objHashcode = obj.hashCode();
        Node node = nodes.get(objHashcode);
        if (node != null) {
            node.putObj(obj);
            return;
        }

        // 找到比给定 key 大的集合
        SortedMap<Integer, Node> tailMap = nodes.tailMap(objHashcode);
        // 找到最小的节点
        int nodeHashcode = tailMap.isEmpty() ? nodes.firstKey() : tailMap.firstKey();
        nodes.get(nodeHashcode).putObj(obj);

    }

    Obj get(Obj obj) {
        Node node = nodes.get(obj.hashCode());
        if (node != null) {
            return node.getObj(obj);
        }

        // 找到比给定 key 大的集合
        SortedMap<Integer, Node> tailMap = nodes.tailMap(obj.hashCode());
        // 找到最小的节点
        int nodeHashcode = tailMap.isEmpty() ? nodes.firstKey() : tailMap.firstKey();
        return nodes.get(nodeHashcode).getObj(obj);
    }
}

package com.study.hash;

/**
 * @Auther: allen
 * @Date: 2019/3/14 19:25
 */
public class NodeArray {

    Node[] nodes = new Node[1024];
    int size = 0;

    public void addNode(Node node) {
        nodes[size++] = node;
    }

    Obj get(Obj obj) {
        int index = obj.hashCode() % size;
        return nodes[index].getObj(obj);
    }

    void put(Obj obj) {
        int index = obj.hashCode() % size;
        nodes[index].putObj(obj);
    }
}



package com.study.hash;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: allen
 * @Date: 2019/3/14 19:25
 */
public class Node {

    Map<Integer, Obj> node = new HashMap<>();
    String name;

    Node(String name) {
        this.name = name;
    }

    public void putObj(Obj obj) {
        node.put(obj.hashCode(), obj);
    }

    Obj getObj(Obj obj) {
        return node.get(obj.hashCode());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

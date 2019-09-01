package com.study.hash;

/**
 * @Auther: allen
 * @Date: 2019/3/14 19:25
 */
public class Test {

    /**
     * 验证普通 hash 对于增减节点，原有会不会出现移动。
     */
    public static void main(String[] args) {

        //NodeArray nodeArray = new NodeArray();
        NodeArray_new nodeArray = new NodeArray_new();

        Node[] nodes = {
                new Node("Node--> 1"),
                new Node("Node--> 2"),
                new Node("Node--> 3")
        };

        for (Node node : nodes) {
            nodeArray.addNode(node);
        }

        Obj[] objs = {
                new Obj("1"),
                new Obj("2"),
                new Obj("3"),
                new Obj("4"),
                new Obj("5")
        };

        for (Obj obj : objs) {
            nodeArray.put(obj);
        }

        validate(nodeArray, objs);
    }

    private static void validate(NodeArray_new nodeArray, Obj[] objs) {
        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }

        nodeArray.addNode(new Node("anything1"));
        nodeArray.addNode(new Node("anything2"));

        System.out.println("========== after  =============");

        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }
    }

}

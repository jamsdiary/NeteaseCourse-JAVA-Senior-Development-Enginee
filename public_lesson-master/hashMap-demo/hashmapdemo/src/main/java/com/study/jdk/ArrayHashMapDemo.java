package com.study.jdk;

import java.util.HashMap;

public class ArrayHashMapDemo {
    Item[] items ;
    public ArrayHashMapDemo(int max) {
        items = new Item[max];
    }

    /** 定义：存储的类型 */
    class Item {
        public Item(String key, String value) {
            this.value = value;
            this.key = key;
        }
        String key;
        String value;
    }

    // 操作的先后顺序插入
    int size = 0;
    public void put(String key, String value) {
        items[size] = new Item(key, value);
        size ++;
    }

    // 查询
    public Item get(String key) {
        for (Item item : items) {
            if(item != null && item.key.equals(key)) {
                return item;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        ArrayHashMapDemo objectHashMap = new ArrayHashMapDemo(16);
        objectHashMap.put("1024", "tony");
        objectHashMap.put("9527", "allen");
        objectHashMap.put("7458", "mengmeng");
        System.out.println(objectHashMap.get("7458").value);

        HashMap hashMap = new HashMap(3);
        hashMap.put("a", "1");

        int cap = 513; // 1024

        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println((n < 0) ? 1 :  n + 1);
    }
}

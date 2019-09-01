package com.study.juc.map;

import java.util.HashMap;

// 测试将对象作为key
public class ObjectHashMapDemo {

    public static void main(String[] args) {
        HashMap<User, String> map = new HashMap<>();
        User user = new User("tony");
        map.put(user, "test");
        System.out.println(map.get(user)); // 1、 输出什么  test
        user = new User("tony");
        System.out.println(map.get(user));  // 2、 输出什么 test
    }
}

class User {
    public String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((User)obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}


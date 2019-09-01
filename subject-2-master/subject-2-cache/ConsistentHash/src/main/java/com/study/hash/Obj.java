package com.study.hash;

/**
 * @Auther: allen
 * @Date: 2019/3/14 19:24
 */
public class Obj {
    String key;
    Obj(String key) {
        this.key = key;
    }
    @Override
    public int hashCode() {
        return key.hashCode();
    }
    @Override
    public String toString() {
        return "Obj{" +
                "key='" + key + '\'' +
                '}';
    }
}


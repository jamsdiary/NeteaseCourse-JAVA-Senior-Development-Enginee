package com.study.jmm;

public class StackOverflowErrorDemo {

    public void test(){
        test();
    }

    public static void main(String[] args) {
        new StackOverflowErrorDemo().test();
    }
}

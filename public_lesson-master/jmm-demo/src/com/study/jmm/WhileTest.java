package com.study.jmm;

public class WhileTest {
    static int x = 1;


    public static void main(String[] args) throws InterruptedException {
        while (x < 10) {
            x++;
        }

    }
}

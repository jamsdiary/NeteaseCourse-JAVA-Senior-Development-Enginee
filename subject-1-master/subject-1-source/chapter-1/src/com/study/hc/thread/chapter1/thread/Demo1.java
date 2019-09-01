package com.study.hc.thread.chapter1.thread;

/**
 * 示例1 - JAVA程序执行分析
 */
public class Demo1 {
	public int x;

	public int sum(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) {
		Demo1 demo1 = new Demo1();
		demo1.x = 3;
		int y = 2;
		int z = demo1.sum(demo1.x, y);
		System.out.println("person age is " + z);
	}


}

package com.study.rabbitmq;

public class YHTriangle {
	/*
	 * 此方法传入一个参数n，表示打印n行的杨辉三角排列
	 */
	public int[][] print(int n) {
		// 此二维数组用来存放杨辉三角中每一行的值
		int[][] number = new int[n][n];
		for (int i = 0; i < n; i++) {// for循环行数
			// 定义临时数组用来存放每行的数值
			int[] a = new int[i + 1];
			for (int j = 0; j < a.length; j++) {
				// 这里判断若是每行的开头或者结尾则存入1，如是开头和结尾之间的值则等于上一行的当前列的前一个数和上一行的当前列的数之和
				if (j == 0 || j == a.length - 1) {
					a[j] = 1;
				} else {
					a[j] = number[i - 1][j - 1] + number[i - 1][j];
				}
			}
			// 这里将每一行存入二维数组中
			for (int k = 0; k < a.length; k++) {
				number[i][k] = a[k];
			}
		}
		return number;
	}

	// 打印输出
	public static void main(String[] args) {
		int[][] number = new YHTriangle().print(6);
		for (int i = 0; i < number.length; i++) {
			for (int j = 0; j < number[i].length; j++) {
				System.out.print(number[i][j] != 0 ? number[i][j] + " " : "");
			}
			System.out.println();
		}
	}
}
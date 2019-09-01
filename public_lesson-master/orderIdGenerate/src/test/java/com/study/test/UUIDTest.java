package com.study.test;

import java.util.UUID;

public class UUIDTest {

	public static void main(String[] args) {
		// 模拟生成100个uuid
		for(int i=0; i<100; i++) {
			System.out.println(UUID.randomUUID());
		}
	}
}

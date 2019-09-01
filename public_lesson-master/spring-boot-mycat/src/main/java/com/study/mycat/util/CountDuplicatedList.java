package com.study.mycat.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class CountDuplicatedList {

	public static void main(String[] args) {

		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("b");
		list.add("c");
		list.add("a");
		list.add("a");
		list.add("a");

		System.out.println("\n例子 1 -统计'a'出现的频率");
		System.out.println("a : " + Collections.frequency(list, "a"));

		System.out.println("\n例子 2 - 统计每一个元素出现的频率");
		// 将List转换为Set
		Set<String> uniqueSet = new HashSet<>(list);
		for (String temp : uniqueSet) {
			System.out.println(temp + ": " + Collections.frequency(list, temp));
		}

		System.out.println("\n例子 3 - 用Map统计每个元素出现的频率");
		Map map = new HashMap();

		for (String temp : list) {
			Integer count = (Integer) map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		}
		printMap(map);

		System.out.println("\nSorted Map");
		Map treeMap = new TreeMap(map);
		printMap(treeMap);

	}

	public static void printMap(Map<String, Entry> map) {

		for (Map.Entry entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}

	}

}
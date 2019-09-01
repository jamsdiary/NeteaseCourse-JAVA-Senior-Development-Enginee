package com.study.mike.rpc.demo;

import java.awt.Point;

public interface DemoService {
	String sayHello(String name);

	Point multiPoint(Point p, int multi);
}

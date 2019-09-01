package com.study.jvm.classloader;

import sun.net.spi.nameservice.dns.DNSNameService;

/** 查看类是被哪一个类加载器加载的 */
public class ViewClassLoaderDemo {
	public static void main(String[] args) {
		System.out.println("\r\n1、 查看 String字符串核心类，对应的加载器");
		System.out.println(String.class.getClassLoader());

		System.out.println("\r\n2、 查看DNSNameService，这是一个ext目录下的拓展包");
		System.out.println(DNSNameService.class.getClassLoader());

		System.out.println("\r\n3、 查看自己写的这个类ViewClassLoaderDemo，对应的加载器");
		System.out.println(ViewClassLoaderDemo.class.getClassLoader());
		System.out.println(ViewClassLoaderDemo.class.getClassLoader().getParent());
		System.out.println(ViewClassLoaderDemo.class.getClassLoader().getParent().getParent());
	}
}

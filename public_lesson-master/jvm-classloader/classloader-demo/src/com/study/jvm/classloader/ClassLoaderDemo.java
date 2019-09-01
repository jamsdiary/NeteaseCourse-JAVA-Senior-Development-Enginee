package com.study.jvm.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoaderDemo {
	public static void main(String[] args) throws Exception {
		// 自己创建一个classLoader实例 ,去加载 class的不同版本（示例中的class，放在jar包里面）
		// 1、 首先要指定 class的位置
		URL jarUrl = new URL("file:\\d:\\dabaojian.jar");
		URLClassLoader parentLoader = new URLClassLoader(new URL[] { jarUrl });
		
		// 每隔3秒加载一次类，加载不同的版本了
		while (true) {
			// TODO 检查文件是否又变化
			// 2、 创建类加载器实例
			URLClassLoader classLoader = new URLClassLoader(new URL[] { jarUrl }, parentLoader);
			// 3、 加载类 service
			Class<?> dabaojian = classLoader.loadClass("com.study.jvm.classloader.service.DabaojianService");
			// 4、 使用
			Object newInstance = dabaojian.newInstance();
			dabaojian.getMethod("taishi").invoke(newInstance);

			newInstance = null;
			classLoader = null;
			System.gc();
			
			Thread.sleep(3000L);
		}
	}
}

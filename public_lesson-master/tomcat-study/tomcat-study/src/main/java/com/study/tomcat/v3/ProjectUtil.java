package com.study.tomcat.v3;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;

public class ProjectUtil {
	/**
	 * 加载Tomcat指定路径下的项目
	 * 
	 * @return 返回每个项目对应的配置
	 * @throws Exception
	 */
	public static Map<String, WebXml> load() throws Exception {
		Map<String, WebXml> result = new HashMap<>();

		String webapps = "d:\\tomcat-study\\";
		// 0、 war部署之前，会自动解压
		// 1、 遍历指定文件夹下面的子文件夹，每个子文件夹当成一个项目
		File[] projects = new File(webapps).listFiles((FileFilter) file -> file.isDirectory());

		for (File projectFile : projects) {
			// 2、 读取每一个项目下面的xml文件
			WebXml webXml = new WebXmlConfigUtil().loadXml(projectFile.getPath() + "\\WEB-INF\\web.xml");
			webXml.projectPath = projectFile.getPath();
			// 3、 jvm加载每个项目下面的class文件和jar包
			webXml.loadServlet();
			// 4、保持每一个项目对应的配置
			result.put(projectFile.getName(), webXml);
		}
		return result;
	}

	/** 配置信息 */
	public class WebXml {
		/** 项目文件夹地址 */
		public String projectPath = null;

		/** Servlet 集合 */
		Map<String, Object> servlets = new HashMap<>();

		/** Servlet 映射 */
		Map<String, String> servletMapping = new HashMap<>();

		/** Servlet 实例集合 */
		Map<String, Servlet> servletInstances = new HashMap<>();

		/**
		 * 使用jdk类加载工具，加载class和jar包
		 * 
		 * @throws Exception
		 */
		public void loadServlet() throws Exception {
			// 定义一个加载class的工具，告诉JVM，类在何方，jar包在哪里？
			URLClassLoader loader = new URLClassLoader(
					new URL[] { new URL("file:" + projectPath + "\\WEB-INF\\classes\\") });

			// 获取项目下配置的所有servlet class定义
			for (Entry<?, ?> entry : this.servlets.entrySet()) {
				String servletName = entry.getKey().toString();
				String servletClassName = entry.getValue().toString();
				// 1、 加载到JVM
				Class<?> loadClass = loader.loadClass(servletClassName);
				// 2、 创建对象，保存起来
				Servlet servlet = (Servlet) loadClass.newInstance();
				this.servletInstances.put(servletName, servlet);
			}
			loader.close();
		}
	}
}

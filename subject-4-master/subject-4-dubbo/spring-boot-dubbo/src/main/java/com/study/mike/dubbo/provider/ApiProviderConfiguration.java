package com.study.mike.dubbo.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.study.mike.dubbo.DemoService;

public class ApiProviderConfiguration {

	public static void main(String[] args) throws Exception {
		// 服务实现
		DemoService demoService = new DemoServiceImpl();

		// 当前应用配置。 请学习ApplicationConfig的API
		ApplicationConfig application = new ApplicationConfig();
		application.setName("hello-world-app");

		// 连接注册中心配置。 请学习RegistryConfig的API
		RegistryConfig registry = new RegistryConfig("224.5.6.7:1234", "multicast");

		// 服务提供者协议配置
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(12345);
		protocol.setThreads(200);

		// 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
		// 服务提供者暴露服务配置。请学习ServiceConfig的API
		// 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
		ServiceConfig<DemoService> service = new ServiceConfig<DemoService>();
		service.setApplication(application);
		service.setRegistry(registry); // 多个注册中心可以用setRegistries()
		service.setProtocol(protocol); // 多个协议可以用setProtocols()
		service.setInterface(DemoService.class);
		service.setRef(demoService);
		service.setVersion("1.0.0");

		// 暴露及注册服务
		service.export();

		System.in.read(); // 按任意键退出
	}
}

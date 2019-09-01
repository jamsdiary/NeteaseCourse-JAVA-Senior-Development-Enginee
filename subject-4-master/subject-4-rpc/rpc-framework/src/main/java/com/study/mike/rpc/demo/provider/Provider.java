package com.study.mike.rpc.demo.provider;

import com.study.mike.rpc.common.protocol.JavaSerializeMessageProtocol;
import com.study.mike.rpc.demo.DemoService;
import com.study.mike.rpc.server.NettyRpcServer;
import com.study.mike.rpc.server.RequestHandler;
import com.study.mike.rpc.server.RpcServer;
import com.study.mike.rpc.server.register.ServiceObject;
import com.study.mike.rpc.server.register.ServiceRegister;
import com.study.mike.rpc.server.register.ZookeeperExportServiceRegister;
import com.study.mike.rpc.util.PropertiesUtils;

public class Provider {
	public static void main(String[] args) throws Exception {

		int port = Integer.parseInt(PropertiesUtils.getProperties("rpc.port"));
		String protocol = PropertiesUtils.getProperties("rpc.protocol");

		// 服务注册
		ServiceRegister sr = new ZookeeperExportServiceRegister();
		DemoService ds = new DemoServiceImpl();
		ServiceObject so = new ServiceObject(DemoService.class.getName(), DemoService.class, ds);
		sr.register(so, protocol, port);

		RequestHandler reqHandler = new RequestHandler(new JavaSerializeMessageProtocol(), sr);

		RpcServer server = new NettyRpcServer(port, protocol, reqHandler);
		server.start();
		System.in.read(); // 按任意键退出
		server.stop();
	}
}

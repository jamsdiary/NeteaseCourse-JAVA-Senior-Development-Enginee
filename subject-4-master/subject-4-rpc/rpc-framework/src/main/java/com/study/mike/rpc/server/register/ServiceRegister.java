package com.study.mike.rpc.server.register;

public interface ServiceRegister {

	void register(ServiceObject so, String protocol, int port) throws Exception;

	ServiceObject getServiceObject(String name) throws Exception;
}

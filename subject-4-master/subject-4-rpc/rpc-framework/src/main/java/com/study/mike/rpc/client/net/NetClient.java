package com.study.mike.rpc.client.net;

import com.study.mike.rpc.discovery.ServiceInfo;

public interface NetClient {
	byte[] sendRequest(byte[] data, ServiceInfo sinfo) throws Throwable;
}

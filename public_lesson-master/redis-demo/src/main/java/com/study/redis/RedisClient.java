package com.study.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/** 云课堂Redis客户端 */
public class RedisClient {

	// 往外写数据
	OutputStream outputStream;
	// 读取服务端返回的数据
	InputStream inputStream;

	// 1、 客户端和服务端是通过tcp网络连接
	public RedisClient(String host, int port) throws Exception {
		// 建立 java客户端和Redis服务端的一个连接
		Socket socket = new Socket(host, port);

		// 往外写数据
		outputStream = socket.getOutputStream();
		// 读取服务端返回的数据
		inputStream = socket.getInputStream();

	}

	// set key value
	public void set(String key, String value) throws Exception {
		// 1、 组装数据
		StringBuffer data = new StringBuffer();
		data.append("*3").append("\r\n");

		data.append("$3").append("\r\n");
		data.append("SET").append("\r\n");

		data.append("$").append(key.getBytes().length).append("\r\n");
		data.append(key).append("\r\n");

		data.append("$").append(value.getBytes().length).append("\r\n");
		data.append(value).append("\r\n");

		// 2、 把指令数据发给Redis服务端
		outputStream.write(data.toString().getBytes());
		System.out.println("发送成功:");
		System.out.println(data);
		// 3、 接收服务端响应
		byte[] response = new byte[1024];
		inputStream.read(response);
		System.out.println("接收到响应：" + new String(response));
	}

	// get key
	public void get(String key) throws Exception {
		// 1、 组装数据
		StringBuffer data = new StringBuffer();
		data.append("*2").append("\r\n");

		data.append("$3").append("\r\n");
		data.append("GET").append("\r\n");

		data.append("$").append(key.getBytes().length).append("\r\n");
		data.append(key).append("\r\n");


		// 2、 把指令数据发给Redis服务端
		outputStream.write(data.toString().getBytes());
		System.out.println("发送成功:");
		System.out.println(data);
		// 3、 接收服务端响应
		byte[] response = new byte[1024];
		inputStream.read(response);
		System.out.println("接收到响应：" + new String(response));
	}

	public static void main(String[] args) throws Exception {
		RedisClient redisClient = new RedisClient("127.0.0.1", 6379);

		redisClient.set("Hello", "Mark Pan & 阿振");
		
		redisClient.get("Hello");
		
	}
}

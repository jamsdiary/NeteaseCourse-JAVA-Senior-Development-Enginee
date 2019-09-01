package com.study.redis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

	@Autowired
	StringRedisTemplate stringRedisTemplate; 
	
	/** 简单Redis测试 */
	@Test
	public void redisTest() {
		// SET hello tony
		// jedis  redistemplate  js go  redisson
		stringRedisTemplate.opsForValue().set("hello", "tony_1");
		// 网络传输了什么数据  ：抓包、欺骗性的服务
	}

	/** 模拟一个Redis-Server 
	 * @throws IOException */
	@Test
	public void redisServerMock() throws IOException {
		ServerSocket serverSocket = new ServerSocket(6379);
		System.out.println("假的Redis服务器启动了");
		Socket connect = serverSocket.accept();
		
		byte[] request = new byte[1024];
		connect.getInputStream().read(request);
		System.out.println("收到来自客户端的请求：");
		System.out.println(new String(request));
	}
	
	
}

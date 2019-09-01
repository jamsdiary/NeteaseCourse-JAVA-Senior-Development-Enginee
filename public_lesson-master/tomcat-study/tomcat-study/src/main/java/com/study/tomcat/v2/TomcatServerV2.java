package com.study.tomcat.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Tomcat服务器-版本1 */
public class TomcatServerV2 {
	private static ExecutorService threadPool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("tomcat 服务器启动成功");
		while (!serverSocket.isClosed()) {
			Socket request = serverSocket.accept();
			threadPool.execute(() -> {
				try {
					// 接收数据、打印
					InputStream inputStream = request.getInputStream();
					System.out.println("收到请求：");
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
					String msg = null;
					while ((msg = reader.readLine()) != null) {
						if(msg.length() == 0) {
							break;
						}
						System.out.println(msg);
					}
					
					// TODO 根据用户请求的URL，调用对应项目里面的Servlet
					
					System.out.println("---------------------end");
					// 响应结果  200
					OutputStream outputStream = request.getOutputStream();
					outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
					outputStream.write("Content-Length: 11\r\n\r\n".getBytes());
					outputStream.write("Hello World".getBytes());
					outputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						request.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
		}
		serverSocket.close();
	}
}

package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

// 示例：命名和输出
public class TEST89465712 {
	 @Test
	    public void test() throws Exception{
	        SocketChannel socketChannel = SocketChannel.open();
	        socketChannel.configureBlocking(false);
	        InetAddress address = InetAddress.getByName("www.baidu.com");
	        socketChannel.connect(new InetSocketAddress(address.getHostAddress(), 80));
	        while (!socketChannel.finishConnect()) {
	            // 没连接上,则一直等待
	            Thread.yield();
	        }
	        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
	        String s = "GET / HTTP/1.1\r\n\r\n";
	        requestBuffer.put(s.getBytes());
	        requestBuffer.flip();
	        while(requestBuffer.hasRemaining()){
	            socketChannel.write(requestBuffer);
	        }
	        requestBuffer.clear();

	        // 读取响应
	        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
	            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
	            if (requestBuffer.position() > 0) break;
	        }
	        requestBuffer.flip();
	        byte[] content = new byte[requestBuffer.limit()];
	        requestBuffer.get(content);
	        String response = new String(content);
	        socketChannel.close();
	        String[] res = response.split("\n");
	        for(String val : res){
	            if(val.startsWith("Server:")){
	                System.out.println("我的QQ号：89465712，我的解析到百度服务器server类型是：" + val);
	            }
	        }
	    }

}
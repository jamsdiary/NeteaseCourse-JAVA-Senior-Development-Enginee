package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TEST625081416 {
	private static final String BAIDU_HOST = "www.baidu.com";
	private static final int BAIDU_PORT = 80;

	public static void main(String[] args) {
		try {
			Selector selector = Selector.open();
			SocketChannel clientSocketChannel = SocketChannel.open();
			clientSocketChannel.connect(new InetSocketAddress(BAIDU_HOST, BAIDU_PORT));
			clientSocketChannel.configureBlocking(false);
			clientSocketChannel.register(selector, SelectionKey.OP_READ);
			StringBuilder reqMsg = new StringBuilder("GET / HTTP/1.1")
					.append("\r\n")
					.append("Host: ")
					.append(BAIDU_HOST)
					.append("\r\n")
					.append("\r\n");
//			System.out.println(reqMsg.toString());
			ByteBuffer buffer = ByteBuffer.wrap(reqMsg.toString().getBytes());
			while (buffer.hasRemaining()) {
				clientSocketChannel.write(buffer);
			}
			selector.select(60 * 1000);
			// 获取事件
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			// 遍历查询结果
			Iterator<SelectionKey> iter = selectionKeys.iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				if (key.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) key.channel();
					try {
						ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
						while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
							if (requestBuffer.position() > 0)
								break;
						}
						if (requestBuffer.position() == 0){
							continue;
						}
						requestBuffer.flip();
						byte[] content = new byte[requestBuffer.limit()];
						requestBuffer.get(content);
						String result = new String(content,"UTF-8");
//						System.out.println("收到数据,来自: " + socketChannel.getRemoteAddress());
						System.out.println("我的QQ号：625081416，我的解析到百度服务器server类型是：" + getServerType(result));
						while (buffer.hasRemaining()) {
							socketChannel.write(buffer);
						}
					} catch (IOException e) {
						e.printStackTrace();
						key.cancel();
					}
				}
				iter.remove();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getServerType(String result) {
		int startInx = result.indexOf("Server");
		result = result.substring(startInx, result.length() - 1);
		int endInx = result.indexOf("\n");
		result = result.substring(0, endInx);
		return result;
	}
}

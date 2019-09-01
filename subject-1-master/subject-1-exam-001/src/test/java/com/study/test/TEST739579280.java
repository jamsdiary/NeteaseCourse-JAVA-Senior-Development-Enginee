package com.study.test;

import org.junit.Test;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

// 示例：命名和输出
public class TEST739579280 {
    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        String header = "GET / HTTP/1.1\r\nHost:www.baidu.com\r\n\r\n";
        byte[] msgBytes = header.getBytes();
        // 发送内容
        ByteBuffer buffer = ByteBuffer.wrap(msgBytes);
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        String contentStr = new String(content);

        //先按行分割，再查找"Server"属性
        String[] lines = contentStr.split("\n");
        for (String str:lines ) {
            if (str.startsWith("Server:")){
                System.out.println("我的QQ号：739579280，我的解析到百度服务器server类型是：" + str);
            }
        }
        socketChannel.close();
    }
}
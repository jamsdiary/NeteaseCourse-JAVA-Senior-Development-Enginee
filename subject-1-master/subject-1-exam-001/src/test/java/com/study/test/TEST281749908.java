package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 示例：命名和输出
public class TEST281749908 {
    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        while (!socketChannel.finishConnect()){
            // 没有连接上，则一直等待
            Thread.yield();
        }
        // 发送请求
        String msg = "GET http://www.baidu.com HTTP/1.1\r\n\r\n";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 长连接下，需要手动判断数据有没有读取结束（此处做一个简单的判断：超过0字节就认为请求结束了）
            if(requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        String responseStr = new String(content);
        socketChannel.close();
        // 提取数据
        String value = "";
        Matcher matcher = Pattern.compile("Server: .*").matcher(responseStr);
        if (matcher.find()) {
            value = matcher.group().split(":")[1].trim();
        }

        System.out.println("我的QQ号：281749908，我的解析到百度服务器server类型是：" + value);
        assert  !value.equals("");
    }
}
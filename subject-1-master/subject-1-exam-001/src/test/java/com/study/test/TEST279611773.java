package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TEST279611773 {

    @Test
    public void test() throws IOException {
        String serverHost = "www.baidu.com";
        int serverPort = 80;
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(serverHost, serverPort));
        while (!socketChannel.finishConnect()) {
            // 如果没连接上,则一直等待
            Thread.yield();
        }

        // 构建request head
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET / HTTP/1.1\r\n");
        stringBuilder.append("Host: ").append(serverHost).append("\r\n");
        stringBuilder.append("Accept-Encoding: gzip, deflate, br\r\n");
        stringBuilder.append("Accept-Language: zh-CN,zh;q=0.9\r\n");
        stringBuilder.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        stringBuilder.append("\r\n");
        String requestHeader = stringBuilder.toString();

        ByteBuffer buffer = ByteBuffer.wrap(requestHeader.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        // response
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(responseBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (responseBuffer.position() > 0) break;
        }
        responseBuffer.flip();
        byte[] content = new byte[responseBuffer.limit()];
        responseBuffer.get(content);
        socketChannel.close();

        String serverName = "";
        String responseStr = new String(content);
        String[] rows = responseStr.split("\\r?\\n");
        for (String row : rows){
            if(row.contains("Server:")){
                serverName = row.split("Server:")[1];
                break;
            }
        }

        System.out.println("我的QQ号：279611773，我的解析到百度服务器server类型是：" + serverName);

    }

}
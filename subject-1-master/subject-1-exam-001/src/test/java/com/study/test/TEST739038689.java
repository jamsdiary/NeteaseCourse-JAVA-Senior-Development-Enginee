package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.util.*;

// 示例：命名和输出
public class TEST739038689 {

    private String serverHost = "www.baidu.com";
    private Integer serverPort = 80;

    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(serverHost, serverPort));
        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }

        // 发送内容
        StringBuilder sbr = new StringBuilder("GET / HTTP/1.1\r\n")
            .append("Host: ").append(serverHost).append("\r\n").append("\r\n");

        ByteBuffer buffer = ByteBuffer.wrap(sbr.toString().getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        // 读取响应
        String resVal = "";
        Scanner sc = new Scanner(socketChannel);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("Server: ")) {
                resVal = line.split(": ")[1];
                break;
            }
        }

        // 关闭连接
        socketChannel.close();
        sc.close();

        System.out.println("我的QQ号：739038689，我的解析到百度服务器server类型是：" + resVal);
        assert resVal.equals("BWS/1.1");
    }

}
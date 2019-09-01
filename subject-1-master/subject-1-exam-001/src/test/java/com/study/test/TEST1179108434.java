package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

// 示例：命名和输出
public class TEST1179108434 {
    @Test
    public void test() throws IOException {
        String value = "xxx";
        // TODO xxoo 发起请求，解析内容
        InetSocketAddress socketAddress = new InetSocketAddress(
                "www.baidu.com", 80);

        SocketChannel channel = SocketChannel.open(socketAddress);
        channel.configureBlocking(false);

        byte[] buf = "GET / HTTP/1.1\r\nHost:www.baidu.com\r\n\r\n".getBytes();

        ByteBuffer bb = ByteBuffer.allocate(buf.length);
        bb.put(buf);
        bb.flip();
        channel.write(bb);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (channel.read(buffer) != -1) {
            buffer.flip();
            if (buffer.limit() > 0) {
                String s = new String(buffer.array());
                if (s.contains("Server:")) {
                    int index1 = s.indexOf("Server:");
                    int index2 = s.indexOf("Set-Cookie");
                    value = s.substring(index1 + 8, index2);
                    System.out.println("我的QQ号：1179108434，我的解析到百度服务器server类型是：" + value);
                }
            }
            buffer.clear();
        }
    }


}
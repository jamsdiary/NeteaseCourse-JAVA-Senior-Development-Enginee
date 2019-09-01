package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 * @create 2019-02-17 11:36
 **/
public class TEST114903407 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
//        socketChannel.connect(new InetSocketAddress("www.zifae.com", 80));
//        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }
        System.out.println("连接建立成功！");
        System.out.println("remote address:" + socketChannel.getRemoteAddress());
        // 发送内容
        String requestMsg = new StringBuffer()
        .append("GET / HTTP/1.1\r\n")
        .append("Host: www.baidu.com\r\n")
        .append("Connection: keep-alive\r\n")
        .append("Cache-Control: max-age=0\r\n")
        .append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n")
        .append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n")
        .append("Accept-Encoding: gzip,deflate,sdch\r\n")
        .append("Accept-Language: zh-CN,zh;q=0.8\r\n")
        .append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n")
        .append("\r\n").toString();
        ByteBuffer buffer = ByteBuffer.wrap(requestMsg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        int read = -1;
        boolean readed = false;
        while (socketChannel.isOpen() && (read = socketChannel.read(byteBuffer)) != -1) {
            if (read == 0 && readed) {
                break;
            } else if (read == 0) {
                continue;
            }
            byteBuffer.flip();// flip方法在读缓冲区字节操作之前调用。
            byte[] bytes = new byte[read];
            byteBuffer.get(bytes);
            String returnMsg = new String(bytes);
            String[] headers = returnMsg.split("\r\n");
            List<String> results = Arrays.stream(headers).filter((s) -> s.contains("Server")).map((s) -> s.split(":")[1]).collect(Collectors.toList());
            System.out.println("我的QQ号：1149034073，我的解析到百度服务器server类型是：" + results.get(0).trim());
            byteBuffer.clear();// 清空缓冲
            readed = true;
        }


        socketChannel.close();

    }
}

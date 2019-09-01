package com.study.test;

import com.sun.tracing.dtrace.ArgsAttributes;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 示例：命名和输出
public class TEST575593413 {
    //@Test
    public static void main(String args[]) throws IOException {
        new TEST575593413().TestBaidu();
    }

    private void TestBaidu() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        socketChannel.configureBlocking(false);
        while (!socketChannel.isConnected()) {
            Thread.yield();
        }
        String msg = "GET / HTTP/1.1\n\n";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        // 发送内容
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        ByteBuffer requstbuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requstbuffer) != -1) {
            if (requstbuffer.position() > 0) break;
        }
        requstbuffer.flip();
        byte[] content = new byte[requstbuffer.limit()];
        requstbuffer.get(content);
        String text = new String(content);
        assert !text.equals("");
        String value = "";

        String regex = "Server:(.*)\r\n";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            value = m.group(1);
        }
        System.out.println("我的QQ号：575593413，我的解析到百度服务器server类型是：" + value);
    }
}
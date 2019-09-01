package com.study.test;

import org.junit.Test;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class TEST43963082 {
    private static Charset charset = Charset.forName("UTF8");

    @Test
    public void test() throws IOException {
        String value = "";
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));

        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }
        String request = "GET / HTTP/1.1\n";
        request += "Host: www.baidu.com\n";
        request += "\n";
        socketChannel.write(ByteBuffer.wrap(request.getBytes()));
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        int size = 0;
        boolean readed = false;
        boolean finish = false;
        while ((size = socketChannel.read(requestBuffer)) != -1){
            if (size == 0 && readed) {
                break;
            } else if (size == 0) {
                continue;
            }
            requestBuffer.flip();
            String text = charset.decode(requestBuffer).toString();
            String[] data = text.split("\n");
            for(String str : data){
                if(str.indexOf("Server:") != -1){
                    value = str;
                    finish = true;
                    break;
                }
            }
            if(finish){
                break;
            }
            requestBuffer.clear();
            readed = true;
        }
        socketChannel.close();
        System.out.println("我的QQ号：43963082，我的解析到百度服务器server类型是：" + value);
        assert !value.equals("");
    }
}
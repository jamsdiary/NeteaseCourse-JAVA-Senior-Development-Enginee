package com.study.test;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TEST997981052 {

    @Test
    public void test997981052() throws Exception
    {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com",80));
        while (!socketChannel.finishConnect())
        {
            Thread.yield();
        }
        System.out.println("connect server");

        String request = "GET / HTTP/1.1\r\n ";
        request += "HOST:www.baidu.com\r\n";
        request += "\r\n";
        ByteBuffer byteBuffer = ByteBuffer.wrap(request.getBytes());
        while (byteBuffer.hasRemaining())
        {
            socketChannel.write(byteBuffer);
        }
        System.out.println("send finish");

        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(byteBuffer1) != -1) {
            if(byteBuffer1.position() > 0)
            {
                break;
            }
        }
        byteBuffer1.flip();
        byte[] content = new byte[byteBuffer1.limit()];
        byteBuffer1.get(content);
        String response = new String(content);
        System.out.println(response);
        String[] results = response.split("\r\n");
        for (String tmp: results ){
            if(tmp.toLowerCase().contains("server"))
            {
                System.out.println("我的QQ号：997981052，我解析到百度的Server类型为：" + tmp);
                return;
            }
        }
    }
}

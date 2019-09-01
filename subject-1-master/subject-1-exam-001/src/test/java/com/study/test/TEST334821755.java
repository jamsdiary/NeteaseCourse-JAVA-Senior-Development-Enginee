package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Scanner;

// 示例：命名和输出
public class TEST334821755 {
    public static HashMap map = new HashMap();
    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com",80));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }

        // 发送内容
        StringBuffer msg = new StringBuffer();
        msg.append("GET / HTTP/1.1\r\n");
        msg.append("HOST: www.baidu.com\r\n\r\n");
        System.out.println(msg);
        ByteBuffer buffer = ByteBuffer.wrap(msg.toString().getBytes("utf-8"));
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.isOpen() && socketChannel.read(responseBuffer) != -1) {
            if (responseBuffer.position() > 0) break;
        }

        responseBuffer.flip();
        socketChannel.close();

        // TODO xxoo 发起请求，解析内容
        System.out.println("我的QQ号：334821755，我的解析到百度服务器server类型是：" + ParseResponse(responseBuffer,"Server"));
        assert  !ParseResponse(responseBuffer,"Server").equals("");
    }
    //获取response指定参数
    public String ParseResponse(ByteBuffer response,String key){

        String[] a = new String(response.array()).split("\r\n\r\n");
        String[] array = a[0].split("\r\n");

        for(String line :array){

            if (line.indexOf(":")>0)

            map.put(line.split(":")[0],line.split(":")[1]);

        }

        return (String) map.get(key);
    }

}
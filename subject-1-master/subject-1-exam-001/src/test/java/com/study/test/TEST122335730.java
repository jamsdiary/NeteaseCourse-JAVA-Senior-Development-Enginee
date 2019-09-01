package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class TEST122335730 {

    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(InetAddress.getByName("www.baidu.com"),80));

        // 发送request请求内容
        StringWriter msg = new StringWriter();
        msg.write("GET / HTTP/1.1\r\n");
        msg.write("Connection: Keep-Alive\r\n");
        msg.write("Host: www.baidu.com\r\n");
        msg.write("Connection: Keep-Alive\r\n\r\n");
        msg.flush();
        ByteBuffer buffer = ByteBuffer.wrap(msg.toString().getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }

        //响应信息读取
        ByteBuffer requestBuffer = ByteBuffer.allocate(2048);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        String response = new String(content);
        socketChannel.close();

        //System.out.println(response);

        //字符串包含判断
        /*
        StringBuffer valueServer = new StringBuffer();
        for(String line : response.split("\r\n")){
            if(line.contains("Server: BWS/1.1")){
                valueServer.append(line);
                break;
            }
        }

        String value = valueServer.toString();
        */

        String value = response.contains("Server: BWS/1.1") == true ? "Server: BWS/1.1" : "";
        System.out.println("我的QQ号：122335730，我的解析到百度服务器server类型是：" + value);
        assert!value.equals("");
    }
}
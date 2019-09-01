package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TEST403160019 {
    @Test
    public void test() {
        NIOClient nioClient = new NIOClient("www.baidu.com", 80);
        nioClient.sendGetRequest();
        String resp = nioClient.receiveResponse();
        String serverType = nioClient.getServer(resp);
        System.out.println("我的QQ号：403160019，我的解析到百度服务器server类型是："+serverType);
        assert true;
    }
    class NIOClient {
        private String hostname;
        private Integer port;
        private SocketChannel socketChannel;

        NIOClient(String hostname, Integer port) {
            this.hostname = hostname;
            this.port = port;
        }

        // 发送请求
        private void sendGetRequest() {
            try{
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                socketChannel.connect(new InetSocketAddress(hostname, port));
                while (!socketChannel.finishConnect()) {
                    // 没连接上,则一直等待
                    Thread.yield();
                }

                // 构造请求头
                String requestHeaderContent = "GET / HTTP/1.1\r\n";
                String requestHeader = requestHeaderContent+ "\r\n";
                // 发送内容
                ByteBuffer buffer = ByteBuffer.wrap(requestHeader.getBytes());

                while (buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 接收响应
        private String receiveResponse() {

            // 读取响应
            ByteBuffer responseBuffer = ByteBuffer.allocate(1024);

            while (true) {
                try {
                    if (!(socketChannel.isOpen() && socketChannel.read(responseBuffer) != -1)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (responseBuffer.position() > 0) break;
            }
            responseBuffer.flip();
            byte[] content = new byte[responseBuffer.limit()];
            responseBuffer.get(content);

            String result = new String(content);

            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        // 从响应体中获取Server
        private String getServer(String resp) {
            String server = "";

            for (String i: resp.split("\n")){
                if ( i.startsWith("Server") ){
//                    System.out.println(i); // Server: BWS/1.1
//                    System.out.println(i.substring(8)); // BWS/1.1
                    server = i.substring(8);
                    break;
                }

            }
            return server;
        }
    }
}


package com.study.test;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TEST240047329 {

    private static final String RN = "\r\n";

    @Test
    public void test() throws Exception{

        StringBuffer requestHeader = new StringBuffer();
        requestHeader.append("GET / HTTP/1.1")
                .append(RN)
                .append("Host: www.baidu.com ")
                .append(RN)
                .append(RN);

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        while (!socketChannel.finishConnect()){
            Thread.yield();
        }
        ByteBuffer requestBuffer = ByteBuffer.wrap(requestHeader.toString().getBytes());
            socketChannel.write(requestBuffer);

        ByteBuffer result = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && (socketChannel.read(result)) != -1) {
            if(result.position() > 0)
                break;
        }
        result.flip();
        byte[]  content =new byte[result.limit()];
        result.get(content);
        String[] resultStr = new String(content).split(RN);
        for (String str : resultStr){
            if(str.startsWith("Server")){
                System.out.println(str);
            }
        }
        socketChannel.close();
    }


}

package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description: http请求返回头部Server信息简单demo
 * @Author: lethez
 * @date: 1/27
 */
public class TEST1039489466 {

    @Test
    public void httpClientDemo() {
        SocketChannel socketChannel = null;
        try {
            InetSocketAddress baiduAddress = new InetSocketAddress("www.baidu.com", 80);
            socketChannel = SocketChannel.open(baiduAddress);
            assert socketChannel != null;
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            //注册SocketChannel到Select
            socketChannel.register(selector, SelectionKey.OP_READ);
  
            //简单构造一个http请求头
            StringBuilder httpHead = new StringBuilder();
            httpHead.append("GET / HTTP/1.1").append("\r\n");
            httpHead.append("Host: www.baidu.com").append("\r\n");
            httpHead.append("Connection: keep-alive").append("\r\n");
            httpHead.append("\r\n");

            while (!socketChannel.finishConnect()) {
                // 没连接上,则一直等待
                Thread.yield();
            }
            //模拟发送请求：
            if (socketChannel.isOpen()) {
                //发起请求
                socketChannel.write(ByteBuffer.wrap(httpHead.toString().getBytes()));
                for (;;) {
                    //设定超时时间1min
                    if (selector.select(60 * 1000) <= 0) break;
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel client = (SocketChannel) key.channel();
                            int length = 0;
                            while ((length = client.read(buffer)) != -1) {
                                String respStr = new String(buffer.array(), "utf-8");
                                String str[] = respStr.split("\r\n");
                                Arrays.stream(str).forEach(s -> {
                                    if (s.contains("Server")) {
                                        System.out.println(s);
                                        //找到满足的条件。直接退出！
                                        System.exit(0);
                                    }
                                });
                            }
                            if (length == -1) {
                                System.out.println("远程连接已关闭！");
                                client.close();
                                key.cancel();
                                System.exit(0);
                            }
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != socketChannel) socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

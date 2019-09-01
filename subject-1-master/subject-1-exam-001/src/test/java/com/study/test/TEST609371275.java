package com.study.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import org.junit.Test;
/**
 * @author by Wendel on 2019/2/17.
 */
public class TEST609371275 {

    @Test
    public void test() throws IOException, InterruptedException {
        String resultFormat = "我的QQ号：609371275，我的解析到百度服务器server类型是：%s";

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("www.baidu.com", 80));
        socketChannel.configureBlocking(false);
        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }
        socketChannel.write(ByteBuffer.wrap(buildRequest()));

        // 只读取1024字节的返回信息
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            if (requestBuffer.position() > 0) {
                break;
            } else {
                // 小优化，百度不会立即返回消息，不sleep会循环太多次
                Thread.sleep(10L);
            }
        }
        requestBuffer.flip();

        // 通过ByteArrayOutputStream读取ByteBuffer中的内容
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(requestBuffer.array(), 0, requestBuffer.limit());

        // 解析目标结果
        BufferedReader reader = new BufferedReader(new StringReader(bos.toString()));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.toUpperCase().startsWith("SERVER:")) {
                System.out.println(String.format(resultFormat, line));
                break;
            }
        }
    }

    // 构建握手协议
    private byte[] buildRequest() {
        return new StringBuilder()
                .append("GET http://www.baidu.com/ HTTP/1.1\r\n")
                .append("\r\n")
                .toString()
                .getBytes();
    }

}

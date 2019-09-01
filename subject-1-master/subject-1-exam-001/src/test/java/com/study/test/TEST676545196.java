package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TEST676545196
{
    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // TODO xxoo 发起请求，解析内容
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        while (!socketChannel.finishConnect()) {
            Thread.yield();
        }
        byte[] msg = "GET / HTTP/1.1\r\nHost:www.baidu.com\r\n\r\n".getBytes();
        ByteBuffer msgBuf = ByteBuffer.wrap(msg);
        while (msgBuf.hasRemaining()) {
            socketChannel.write(msgBuf);
        }
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);

        String responseHeader = getServerLine(new String(content));

        String value = responseHeader.split(":")[1].trim();
        System.out.println("我的QQ号：676545196，我的解析到百度服务器server类型是：" + value);
        assert  !value.equals("");
    }

    public String getServerLine(String response) {
        String pattern = ".*Server.*";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(response);
        if (m.find( )) {
            return m.group(0);
        }
        return "";
    }
}

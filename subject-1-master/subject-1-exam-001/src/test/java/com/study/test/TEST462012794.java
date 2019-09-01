package com.study.test;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Random;
import java.util.concurrent.TimeUnit;

// 示例：命名和输出
public class TEST462012794 {

    private static final String UTF_8 = "UTF-8";
    private static CharsetEncoder encoder = Charset.forName(UTF_8).newEncoder();
    private static CharsetDecoder decoder = Charset.forName(UTF_8).newDecoder();
    private static byte[] request = null;

    static {
        // 模仿chrome浏览器，构建HTTP request
        StringBuffer buf = new StringBuffer();
        buf.append("GET https://www.baidu.com/ HTTP/1.1\r\n");
        buf.append("Host: www.baidu.com\r\n");
        buf.append("Connection: keep-alive\r\n");
        buf.append("Cache-Control: max-age=0\r\n");
        buf.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n");
        buf.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n");
        buf.append("Accept-Encoding: gzip, deflate, br\r\n");
        buf.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
        buf.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        buf.append("\r\n");
        request = buf.toString().getBytes();
    }

    @Test
    public void test() throws Exception {

        String value = "";
        String response = "";
        Charset charset = Charset.forName("GBK");

        // 1. 创建SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        // 2. 建立连接
        socketChannel.connect(new InetSocketAddress("www.baidu.com", 80));
        while (!socketChannel.finishConnect()) {
            sleep();
        }

        // 3. 发请请求
        socketChannel.write(ByteBuffer.wrap(request));

        int read = 0;
        boolean finished = false;
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 4. 读取响应直至结束
        while ((read = socketChannel.read(byteBuffer)) != -1) {
            if (read == 0 && finished) {
                break;
            } else if (read == 0) {
                continue;
            }

            byteBuffer.flip();
            response = charset.decode(byteBuffer).toString();
            byteBuffer.clear();

            finished = true;
        }

        // 5. 解析内容
        value = parseServerFromHeader(response);

        System.out.println("我的QQ号：462012794，我的解析到百度服务器server类型是：" + value);
        assert  !value.equals("");
    }

    private String parseServerFromHeader(String response) {
        if (response == null || response.isEmpty()) {
            return null;
        }
        String[] segments = response.split("\r\n");
        for (String segment : segments) {
            if (segment.startsWith("Server:")) {
                return segment;
            }
        }
        return "";
    }


    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
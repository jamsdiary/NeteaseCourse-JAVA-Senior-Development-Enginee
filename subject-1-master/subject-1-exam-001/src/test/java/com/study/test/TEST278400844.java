package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TEST278400844 {
    @Test
    public  void test() throws IOException {
        Charset charset = Charset.forName("UTF8");
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("www.baidu.com",80));
        String req = "GET / HTTP/1.1\r\n";
        req +="HOST:www.baidu.com\r\n";
        req +="\r\n";
        ByteBuffer encode = charset.encode(req);
        sc.write(encode);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = sc.read(byteBuffer);
        if(read>0){
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            String response = new String(bytes, "UTF8");
            String regex = "Server:(.*)\r\n";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(response);
            String value = "";
            while(m.find()){
                value = m.group(1);
            }
            System.out.println("我的QQ号：278400844，我的解析到百度服务器server类型是：" + value);
            assert  !value.equals("");
        }


    }
}

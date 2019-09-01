package com.study.test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * description:测试baidu访问
 * @date 2019-01-27
 **/
public class TEST2433494265 {

    @Test
    public void testAccessBaidu() throws Exception{
        String requestStr = "GET / HTTP/1.1\r\n\r\n";
        requestStr+= "Host: www.baidu.com\r\n";
        requestStr+= "Connection: keep-alive\r\n";
        requestStr+= "Upgrade-Insecure-Requests: 1\r\n";
        requestStr+= "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36\r\n";
        requestStr+= "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n";
        requestStr+= "Accept-Encoding: gzip, deflate, br\r\n";
        requestStr+= "Accept-Language: zh-CN,zh;q=0.9\r\n";
        requestStr+= "Cookie: locale=zh\r\n\r\n";

        SocketChannel socketChannel = SocketChannel.open();
        //连接到百度服务器的80端口
        if (socketChannel.connect(new InetSocketAddress("www.baidu.com",80))){
            ByteBuffer requestToWrite = ByteBuffer.wrap(requestStr.getBytes());
            while (requestToWrite.hasRemaining()){
                socketChannel.write(requestToWrite);
            }
            ByteBuffer reponseToRead = ByteBuffer.allocate(65535);

            while (socketChannel.isOpen() && socketChannel.read(reponseToRead) != -1){
                if (reponseToRead.position() != 0) break;
            }

            if (reponseToRead.position() > 0){
                reponseToRead.flip();
                byte[] readBytes = new byte[reponseToRead.limit()];
                reponseToRead.get(readBytes);
                String responseContent = new String(readBytes);
                StringReader reader = new StringReader(responseContent);
                BufferedReader bfReader = new BufferedReader(reader);
                String targetContent;
                while ((targetContent = bfReader.readLine()) != null){
                    if (targetContent.contains("Server:")) break;
                }
                System.out.println(targetContent);
                bfReader.close();
            }
        }
    }
}

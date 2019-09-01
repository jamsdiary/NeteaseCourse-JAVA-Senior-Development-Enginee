package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

// 示例：命名和输出
public class TEST170257702 {
    /*
    使用NIO提供的新网络编程工具包，实现访问百度网页(http://www.baidu.com)，获取到百度服务器返回数据的Server:信息。
    程序运行输出结果示例：“我的QQ号：12323，我的解析到百度服务器server类型是：Server: BWS/1.1”
    浏览器截图的数据示例，我们就是要通过NIO网络请求，拿到这个红色框框的数据并且打印出来
     */
    @Test
    public void test() throws IOException {
        String serverAddress = "www.baidu.com";
        int serverPort = 80;

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(serverAddress, serverPort));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }

        // request
        String requestHeader = buildRequestHeader(serverAddress);
        ByteBuffer buffer = ByteBuffer.wrap(requestHeader.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // response
        ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
        while (socketChannel.isOpen() && socketChannel.read(responseBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (responseBuffer.position() > 0) break;
        }
        responseBuffer.flip();
        byte[] content = new byte[responseBuffer.limit()];
        responseBuffer.get(content);
        socketChannel.close();


        String value = parseServerName(content);
        System.out.println("我的QQ号：170257702，我的解析到百度服务器server类型是：" + value);
        assert  !value.equals("");
        assert  value.equals("BWS/1.1");
    }

    private String parseServerName(byte[] content) {
        String serverNameValue = "";
        //在response 的header里，拿到rawResponse字符串
        String rawResponse = new String(content);
        /*
        HTTP/1.1 302 Found
        Connection: Keep-Alive
        Content-Length: 225
        Content-Type: text/html
        Date: Sat, 16 Feb 2019 07:10:50 GMT
        Location: https://www.baidu.com/
        P3p: CP=" OTI DSP COR IVA OUR IND COM "
        Server: BWS/1.1
        Set-Cookie: BAIDUID
        */
        String[] rows = rawResponse.split("\\r?\\n");
        for (String row : rows){
            if(row.contains("Server:")){
                serverNameValue = row.replace("Server: ", "");
                break;
            }
        }
        return serverNameValue;
    }

    private String buildRequestHeader(String serverAddress) {
        //浏览器抓包得到http请求的GET header内容
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GET / HTTP/1.1\r\n");
        stringBuilder.append("Host: ").append(serverAddress).append("\r\n");
        stringBuilder.append("Connection: keep-alive\r\n");
        stringBuilder.append("Cache-Control: max-age=0\r\n");
        stringBuilder.append("User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36\r\n");
        stringBuilder.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n");
        stringBuilder.append("Accept-Encoding: gzip, deflate, br\r\n");
        stringBuilder.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
        stringBuilder.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }
}
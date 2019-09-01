package com.study.test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author 376277
 * @version 1.0
 * @ClassName TEST1061569482
 * @Description TODO
 * @date 2019/2/19 12:16
 **/
public class TEST1061569482 {

    //使用NIO提供的新网络编程工具包，实现访问百度网页(http://www.baidu.com)，获取到百度服务器返回数据的Server:信息。
    //程序运行输出结果示例：“我的QQ号：12323，我的解析到百度服务器server类型是：Server: BWS/1.1”
    //浏览器截图的数据示例，我们就是要通过NIO网络请求，拿到这个红色框框的数据并且打印出来


    public static void main(String[] args) throws Exception {

        SocketChannel client = SocketChannel.open();
        //设置为非阻塞并设置连接地址
        client.configureBlocking(false);
        client.connect(new InetSocketAddress("www.baidu.com", 80));
        //判断你是否连接成功
        while(!client.finishConnect()){
            //如果没有连接成功，一直等待
            Thread.yield();
        }
        //System.out.println("---> 已经成功连接上服务器");
        //给服务器发送数据，让浏览器响应
        String context=
                "GET / HTTP/1.1\r\n" +
                        "Host: www.baidu.com\r\n" +
                        "Connection: keep-alive\r\n" +
                        "Upgrade-Insecure-Requests: 1\r\n" +
                        "User-Agent: Mozilla/5.0\r\n" +
                        "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                        "Accept-Language: zh-CN,zh;q=0.8\r\n\r\n"; //根据http协议，头部信息和请求内容必须有空行
        ByteBuffer request = ByteBuffer.wrap(context.getBytes());
        //System.out.println(context);
        while(request.hasRemaining()){
            client.write(request);
        }

        //获取收到的数据
        ByteBuffer response = ByteBuffer.allocate(1024 * 1024);
        while (client.read(response) <= 0){
            //System.out.println("-->");
        }
        //System.out.println("-->读取后buffer位置" + response.position());
        //转换buffer为读取模式
        response.flip();
        byte[] data = new byte[response.limit()];
        response.get(data);
        String resStr = new String(data);
        //截取第一个空行前的数据
        String subStr = resStr.substring(0, resStr.indexOf("\r\n\r\n"));
        //System.out.println(subStr);
        //根据换行符进行分割字符串
        String[] splitStrs = subStr.split("\r\n");
        //查找以Server:开头的字符串
        for(String str : splitStrs){
            if(str.startsWith("Server:")){
                System.out.println("我的QQ号：1061569482，我的解析到百度服务器server类型是：" + str);
            }
        }

        //System.out.println("收到回复：" + new String(data));
        client.close();
    }


}

package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TEST9225886 {

    @Test
    public void test() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        //TODO 阻塞模式下如何通过selector去控制数据完整读取？
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("www.baidu.com",80));
        //确保连接成功
        while(!socketChannel.finishConnect()){
        	Thread.yield();
        }
        //模拟HTTP-HEADER
        StringBuffer sb = new StringBuffer();
        sb.append("GET / HTTP/1.1\r\n");
        sb.append("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n");
        sb.append("Accept-Language: zh-CN,zh;q=0.9\r\n");
        sb.append("Connection: keep-alive\r\n");
        sb.append("Host: www.baidu.com\r\n");
        sb.append("\r\n");
        socketChannel.write(ByteBuffer.wrap(sb.toString().getBytes("utf-8")));

        //读取返回值
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
        while(socketChannel.isOpen() && socketChannel.read(requestBuffer)!=-1){
        	//暂时用老师课堂上的方式简易处理
        	if(requestBuffer.position()>0)
        		break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        String ret = new String(content);
        List<String> alist = Arrays.asList(ret.split("\r\n"));
        String value = "";
        //简单判断, 应该用正则
        for(String s : alist){
        	if(s.contains("Server:")){
        		if(s.split(":").length>1){
        			value = s.split(":")[1];
        		}
        	}
        }
        System.out.println("我的QQ号：9225886，我的解析到百度服务器server类型是：" + value);
        assert  !value.equals("");
        socketChannel.close();
    }

}

package com.study.netty.push.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TonySocketServer {
    public static void main(String[] args) throws IOException, Exception {
        // server
        ServerSocket serverSocket = new ServerSocket(9999);

        // 获取新连接
        while (true) {
            final Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            while (true) {
                byte[] request = new byte[1024];
                int read = inputStream.read(request);
                if (read == -1) {
                    break;
                }
                // 得到请求内容，解析，得到发送对象和发送内容
                String content = new String(request);
                System.out.println(content);
            }
        }
    }
}

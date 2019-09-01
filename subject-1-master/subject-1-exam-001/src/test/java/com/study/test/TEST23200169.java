package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import org.junit.Test;

public class TEST23200169 {

  @Test
  public void test() throws IOException, URISyntaxException {
    String hostname = new URI("http://www.baidu.com/").getHost();
    int port = 80;
    SocketAddress remote = new InetSocketAddress(hostname, port);
    SocketChannel channel = SocketChannel.open(remote);
    channel.configureBlocking(false);
    String request = "GET / HTTP/1.1\r\n"
        + "Accept: text/*\r\n"
        + "Connection: close\r\n"
        + "Host: " + hostname + "\r\n"
        + "\r\n";
    ByteBuffer header = ByteBuffer.wrap(request.getBytes());
    channel.write(header);
    Scanner sc = new Scanner(channel);
    while (sc.hasNextLine()) {
      String line = sc.nextLine();
      if (line.startsWith("Server: ")) {
        String bwsValue = line.split(" *: *")[1];
        System.out.println("我的QQ号：23200169，我的解析到百度服务器server类型是：" + bwsValue);
        break;
      }
    }
    channel.close();
    sc.close();
  }
}

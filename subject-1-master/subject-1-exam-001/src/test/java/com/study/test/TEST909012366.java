package com.study.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;

public class TEST909012366 {

  @Test
  public void readServer() throws IOException {
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);

    Selector selector = Selector.open();
    channel.register(selector, SelectionKey.OP_CONNECT);
    channel.connect(new InetSocketAddress("www.baidu.com", 80));
    while (true) {
      if (!selector.isOpen()) {
        break;
      }

      selector.select();
      Set<SelectionKey> selectionKeys = selector.selectedKeys();
      Iterator<SelectionKey> iterator = selectionKeys.iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        iterator.remove();
        if (key.isConnectable()) {
          connect(key, selector);
        }

        if (key.isWritable()) {
          write(key, selector);
        }

        if (key.isReadable()) {
          read(key, selector);
        }
      }
    }
  }

  private void write(SelectionKey key, Selector selector) throws IOException {
    SocketChannel channel = (SocketChannel) key.channel();
    channel.register(selector, SelectionKey.OP_READ);
    channel.write(ByteBuffer.wrap("GET / HTTP/1.1\r\n\r\n".getBytes()));
  }

  private void read(SelectionKey key, Selector selector) throws IOException {
    SocketChannel channel = (SocketChannel) key.channel();
    ByteBuffer buffer = ByteBuffer.wrap(new byte[1024]);
    int len = channel.read(buffer);
    if (len == -1) {
      channel.close();
      return;
    }

    printServerFromResponse(new String(buffer.array(), 0, len));
    channel.close();
    selector.close();
  }

  private void printServerFromResponse(String response) {
    int index = response.indexOf("Server: ");
    int end = response.indexOf("\r\n", index);
    String server = response.substring(index + 8, end);
    System.out.println(buildResult(server));
  }

  private String buildResult(String server) {
    return "我的QQ号：909012366，我的解析到百度服务器server类型是：Server: " + server;
  }

  private void connect(SelectionKey key, Selector selector) throws IOException {
    SocketChannel channel = (SocketChannel) key.channel();
    channel.finishConnect();
    channel.register(selector, SelectionKey.OP_WRITE);
  }

}

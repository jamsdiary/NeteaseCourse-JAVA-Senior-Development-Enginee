package com.study.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

// 示例：命名和输出
public class TEST894559632 {
    @Test
    public void test() throws IOException {

        Pattern pattern = Pattern.compile ("Server: BWS/1.1");

        SocketChannel socketChannel = SocketChannel.open ();
        socketChannel.configureBlocking (false);

        Selector connSelector = Selector.open ();
        Selector readSelector = Selector.open ();

        socketChannel.register (connSelector, SelectionKey.OP_CONNECT, socketChannel);
        socketChannel.register (readSelector, SelectionKey.OP_READ, socketChannel);

        socketChannel.connect (new InetSocketAddress ("www.baidu.com", 80));

        String request = "GET https://www.baidu.com HTTP/1.1\r\n" + "\r\n";

        String server = null;

        while (Objects.isNull (server)) {
            connSelector.select ();

            Set<SelectionKey> connKeys = connSelector.selectedKeys ();

            Iterator<SelectionKey> connIter = connKeys.iterator ();

            while (connIter.hasNext ()) {
                connIter.next ();
                connIter.remove ();
                if (socketChannel.isConnectionPending ()) {
                    socketChannel.finishConnect ();
                }
                socketChannel.write (ByteBuffer.wrap (request.getBytes ()));

                readSelector.select ();
                Set<SelectionKey> readKeys = readSelector.selectedKeys ();
                Iterator<SelectionKey> readIter = readKeys.iterator ();

                while (readIter.hasNext ()) {
                    readIter.next ();
                    readIter.remove ();

                    ByteBuffer buffer = ByteBuffer.allocate (1024);

                    while (socketChannel.isOpen () && socketChannel.read (buffer) != -1) {
                        if (buffer.position () > 0) break;

                    }

                    buffer.flip ();
                    byte[] bytes = new byte[buffer.limit ()];
                    buffer.get (bytes);
                    String response = new String (bytes);

                    String[] split = response.split ("\r\n");
                    for (String s : split) {
                        if (s.startsWith ("Server:")) {
                            server = s.split (":")[1].trim ();
                            break;
                        }
                    }

                }
            }

        }

        System.out.println ("我的QQ号：894559632，我的解析到百度服务器server类型是：" + server);
        assert !server.equals ("");
    }
}
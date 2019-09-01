package com.study.hc.net.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TonyNioHttpServer {

    public static Selector selector;

    // 定义线程池
    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(25, 25, 25,
            TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private static ServerSocketChannel socketChannel;

    private static final int port = 8080;

    public static void main(String[] args) throws Exception {

        // serversocket
        socketChannel = ServerSocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.bind(new InetSocketAddress(port));

        System.out.println("NIO启动:" + port);
        // 获取一个选择器
        // 底层的事件通知机制
        // 老板娘 selector
        TonyNioHttpServer.selector = Selector.open();

        // 登记： 表示对这个通道上OP_ACCEPT事件感兴趣，并且返回一个标记
        // 此处表示，希望收到socket通道8080端口上建立连接这个通知
        SelectionKey selectionKey = socketChannel.register(TonyNioHttpServer.selector, 0);
        selectionKey.interestOps(selectionKey.OP_ACCEPT);
        
        while (true) { // 带几个美女，坐在大厅

            // 如果没有新的socket与服务器有连接或者是数据交互，这里就会等待1秒
            TonyNioHttpServer.selector.select(1000);

            // 开始处理
            Set<SelectionKey> selected = TonyNioHttpServer.selector.selectedKeys();
            Iterator<SelectionKey> iter = selected.iterator();
            while (iter.hasNext()) {
                // 获取注册在上面标记
                SelectionKey key = iter.next();

                if (key.isAcceptable()) { // 判断是否OP_ACCEPT的通知
                    // 处理连接
                    System.out.println("有新的连接啦，当前线程数量:"
                            + TonyNioHttpServer.threadPoolExecutor.getActiveCount());
                    // 有新的连接，赶紧接客
                    SocketChannel chan = socketChannel.accept();
                    // 问一下价格多少，需要什么样服务...
                    chan.configureBlocking(false);
                    // 注册一个新监听。
                    // 表示希望收到该连接上OP_READ数据传输事件的通知
                    chan.register(TonyNioHttpServer.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) { // OP_READ
                    // 取出附着在上面的信息，也就是上面代码中附着的连接信息
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    // 处理中，不需要收到任何通知
                    key.cancel();
                    // tomcat 大保健旗舰店 有200技师，只有付钱的客户才会享受技师 泰式、保shen，
                    socketChannel.configureBlocking(false);
                    TonyNioHttpServer.threadPoolExecutor.execute(() -> {
                        try {
                            // 读取里面的内容，请注意，此处大小随意写的。
                            // tomcat中会根据Http协议中定义的长度来读取数据，或者一直读到通道内无数据为止
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            socketChannel.read(byteBuffer);
                            byteBuffer.flip(); // 转为读模式
                            String request = new String(byteBuffer.array());

                            System.out.println("收到新数据，当前线程数："
                                    + TonyNioHttpServer.threadPoolExecutor.getActiveCount()
                                    + "，请求内容：" + request);
                            // 给一个当前时间作为返回值
                            // 随意返回，不是Http的协议
                            byteBuffer.clear();
                            ByteBuffer wrap = ByteBuffer
                                    .wrap(("tony" + System.currentTimeMillis()).getBytes());
                            socketChannel.write(wrap);
                            wrap.clear();
                            socketChannel.configureBlocking(false);
                            // 注册一个新监听。 表示希望收到该连接上OP_READ事件的通知
                            socketChannel.register(TonyNioHttpServer.selector,
                                    SelectionKey.OP_READ);
                        } catch (Exception e) {
                            // e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " 服务器线程处理完毕，当前线程数："
                                + threadPoolExecutor.getActiveCount());
                    });
                }
                // 取出后删除
                iter.remove();
            }
            selected.clear();
            // 过掉cancelled keys
            TonyNioHttpServer.selector.selectNow();
        }
    }
}

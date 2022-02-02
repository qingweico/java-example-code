package io.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zqw
 * @date 2021/10/18
 */
public class NioPlanEchoServer {
    public static void serve(int port) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        ServerSocket socket = channel.socket();
        InetSocketAddress address = new InetSocketAddress(port);

        socket.bind(address);
        // Non-Block, in the mode of Blocking, the operation of regist is not allowed, or
        // throw IllegalBlockingModeException.
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Listening for connection on port: " + port);
        while (true) {
            try {
                // 通过一个阻塞对象监听多路连接请求;
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from: " + client.getRemoteAddress());
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ,
                                ByteBuffer.allocate(1000));

                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        int read = client.read(output);
                        System.out.println("client " + client.getRemoteAddress() + " send: " +
                                new String(output.array(), 0, read));
                    }
                    if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                    iterator.remove();
                } catch (IOException e) {
                    key.cancel();
                    key.channel().close();
                }
            }
        }

    }
    public static void main(String[] args) throws IOException {
        serve(8080);
    }
}

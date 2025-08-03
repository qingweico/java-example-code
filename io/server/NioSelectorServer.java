package io.server;

import cn.qingweico.io.Print;

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
 * -------------------------------- 基于 IO 多路复用 --------------------------------
 * @author zqw
 * @date 2021/10/18
 * @see NioPlainServer
 */
class NioSelectorServer {
    public static void serve(int port) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        ServerSocket socket = channel.socket();
        InetSocketAddress address = new InetSocketAddress(port);

        socket.bind(address);
        // Non-Block, in the mode of Blocking, the operation of registering is not allowed, or
        // throw IllegalBlockingModeException.
        // BIO >> accept >> Non-Block
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Listening for connection on port: " + port);
        while (true) {
            try {
                // 通过一个阻塞对象监听多路连接请求;
                selector.select();
            } catch (IOException e) {
                Print.err(e.getMessage());
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
                        // BIO >> read >> Non-Block
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ,
                                ByteBuffer.allocate(1000));
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        int read = client.read(output);
                        if (read > 0) {
                            System.out.println("client " + client.getRemoteAddress() + " send: " +
                                    new String(output.array(), 0, read));
                        } else if (read == -1) {
                            // TODO
                            System.out.println("client disconnection: " + client.getRemoteAddress());
                        }
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

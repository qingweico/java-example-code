package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author:qiming
 * @date: 2021/12/30
 */
public class SocketNIO {
    public static void main(String[] args) throws IOException {
        LinkedList<SocketChannel> clients = new LinkedList<>();

        ServerSocketChannel channel = ServerSocketChannel.open();

        channel.bind(new InetSocketAddress(9090));
        channel.configureBlocking(false);

    }
}

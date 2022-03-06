package io.server;

import util.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zqw
 * @date 2022/2/25
 */
public class NioPlainServer {

    public static void main(String[] args) throws IOException {
        List<SocketChannel> channelList = new ArrayList<>();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(Constants.QOMOLANGMA));
        channel.configureBlocking(false);
        System.out.println("server start");
        while (true) {
            try {
                SocketChannel socketChannel = channel.accept();
                if (socketChannel != null) {
                    System.out.println("accept connection: " + socketChannel.getRemoteAddress());
                    socketChannel.configureBlocking(false);
                    channelList.add(socketChannel);
                }
                Iterator<SocketChannel> iterator = channelList.iterator();
                while (iterator.hasNext()) {
                    SocketChannel sc = iterator.next();
                    ByteBuffer buffer = ByteBuffer.allocate(Constants.KB);
                    sc.configureBlocking(false);
                    int read = sc.read(buffer);
                    if (read > 0) {
                        System.out.println(new String(buffer.array(), 0, read));
                    } else if(read == -1){
                        iterator.remove();
                        System.out.println("client disconnection: " + sc.getRemoteAddress());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}

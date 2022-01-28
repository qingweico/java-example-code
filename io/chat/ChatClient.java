package io.chat;

import thread.pool.CustomThreadPool;
import util.Constants;
import util.DateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author zqw
 * @date 2022/1/26
 */
public class ChatClient {
    private static final String HOST = Constants.LOOP_BACK;
    private static final int PORT = Constants.QOMOLANGMA;
    private Selector selector;
    private SocketChannel socketChannel;
    private String username;

    public ChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("client " + socketChannel.getLocalAddress() + " connected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendMegToServer(String msg) {
        try {
            msg = username + ": " + msg;
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            System.out.printf("[%s] server error%n", DateUtil.format());
        }
    }

    public void readMsgFromServer() {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1000);
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        int read = socketChannel.read(buffer);
                        System.out.printf("[%s] %s%n", DateUtil.format(),
                                new String(buffer.array(), 0, read));
                    }
                    iterator.remove();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        CustomThreadPool pool = new CustomThreadPool(2, 1);
        pool.execute(chatClient::readMsgFromServer);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            chatClient.sendMegToServer(msg);
        }
    }
}

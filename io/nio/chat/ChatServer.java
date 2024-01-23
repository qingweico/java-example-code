package io.nio.chat;

import util.Print;
import util.constants.Constants;
import util.DateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zqw
 * @date 2022/1/26
 */
public class ChatServer {
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public ChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Listening for connection on port: " + PORT);
        } catch (IOException ex) {
            Print.err(ex.getMessage());
        }
    }

    public void listener() {
        while (true) {
            try {
                selector.select();
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        String clientName = socketChannel.getRemoteAddress().toString().substring(1);
                        System.out.printf("[%s] Accepted connection from: %s%n",
                                DateUtil.format(), clientName);
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1000));
                        // 向其他客户端提示上线
                        String msg = "[client " + clientName + " on line]";
                        relay(msg, socketChannel, false);
                    }
                    if (selectionKey.isReadable()) {
                        readMessage(selectionKey);
                    }
                    iterator.remove();
                }

            } catch (IOException ex) {
                Print.err(ex.getMessage());
                break;
            }
        }
    }

    private void readMessage(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1000);
            int readBytes = socketChannel.read(buffer);
            String msg = new String(buffer.array(), 0, readBytes);
            relay(msg, socketChannel, true);
        } catch (IOException ex) {
            try {
                String clientName = socketChannel.getLocalAddress().toString().substring(1);
                System.out.printf("[%s] %s has disconnected... %n",
                        DateUtil.format(), clientName);
                // 向其他客户端提示下线
                String msg = "[client " + clientName + " off line]";
                relay(msg, socketChannel, false);
                key.cancel();
                socketChannel.close();
            } catch (IOException e) {
                Print.err(e.getMessage());
            }
        }
    }

    private void relay(String msg, SocketChannel sender, boolean textMessage) throws IOException {
        if (textMessage) {
            System.out.printf("[%s] received the message from %s%n", DateUtil.format(),
                    sender.getRemoteAddress().toString().substring(1));
        }
        try {
            for (SelectionKey selectionKey : selector.keys()) {
                SelectableChannel selectableChannel = selectionKey.channel();
                if (selectableChannel instanceof SocketChannel) {
                    SocketChannel socketChannel = (SocketChannel) selectableChannel;
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    if (textMessage) {
                        System.out.printf("[%s] message relaying...%n", DateUtil.format());
                        socketChannel.write(buffer);
                        System.out.printf("[%s] message has forwarding to %s%n", DateUtil.format(),
                                socketChannel.getRemoteAddress().toString().substring(1));
                    } else {
                        if (selectableChannel != sender) {
                            socketChannel.write(buffer);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.printf("[%s] server error%n", DateUtil.format());
        }

    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listener();
    }
}

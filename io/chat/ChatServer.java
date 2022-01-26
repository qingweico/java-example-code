package io.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zqw
 * @date 2022/1/26
 */
public class ChatServer {
    private static final int PORT = 8888;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private final SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Date date = new Date();

    public ChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Listening for connection on port: " + PORT);
        } catch (IOException ex) {
            ex.printStackTrace();
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
                        System.out.printf("[%s] Accepted connection from: %s%n",
                                sfd.format(date.getTime()), socketChannel.getRemoteAddress().toString().substring(1));
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1000));
                    }
                    if (selectionKey.isReadable()) {
                        readMessage(selectionKey);
                    }
                    iterator.remove();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
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
            relayMessage(msg, socketChannel);
        } catch (IOException ex) {
            try {
                System.out.printf("[%s] %s has disconnected... %n",
                        sfd.format(date.getTime()), socketChannel.getRemoteAddress().toString().substring(1));
                key.cancel();
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void relayMessage(String msg, SocketChannel sender) throws IOException {
        System.out.printf("[%s] received the message from %s%n", sfd.format(date.getTime()), sender.getRemoteAddress().toString().substring(1));
        try {
            for (SelectionKey selectionKey : selector.keys()) {
                SelectableChannel selectableChannel = selectionKey.channel();
                if (selectableChannel instanceof SocketChannel) {
                    System.out.printf("[%s] message relaying...%n", sfd.format(date.getTime()));
                    SocketChannel socketChannel = (SocketChannel) selectableChannel;
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                    socketChannel.write(buffer);
                    System.out.printf("[%s] message has forwarding to %s%n", sfd.format(date.getTime()),
                            socketChannel.getRemoteAddress().toString().substring(1));
                }
            }
        } catch (Exception ex) {
            System.out.printf("[%s] server error%n", sfd.format(date.getTime()));
        }

    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listener();
    }
}

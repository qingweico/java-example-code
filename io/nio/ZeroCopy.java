package io.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zqw
 * @date 2022/1/27
 */
public class ZeroCopy {

    private final String fileName = "";
    private final String host = "127.0.0.1";
    private final int port = 8848;
    private final int bufferSize = 1024;
    private final int eof = -1;

    @Test
    public void IOServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(60 * 1000);
        int rcvBytes = 0;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[bufferSize];
                while (true) {
                    int read = in.read(buffer);
                    if (read == eof) {
                        break;
                    }
                    rcvBytes += read;
                }
                System.out.printf("[rcvBytes: %s, from client %s]%n", rcvBytes, socket.getRemoteSocketAddress());
            } catch (SocketTimeoutException e) {
                System.out.println("socket timeout");
                break;
            }
        }

    }

    @Test
    public void IOClient() throws IOException {
        Socket socket = new Socket(host, port);
        FileInputStream fis = new FileInputStream(fileName);
        OutputStream os = socket.getOutputStream();
        byte[] buffer = new byte[bufferSize];
        int sendBytes = 0;
        long start = System.currentTimeMillis();
        while (true) {
            try {
                int read = fis.read(buffer);
                if (read == eof) {
                    break;
                }
                sendBytes += read;
                os.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        os.close();
        fis.close();
        socket.close();
        System.out.printf("[sendBytes: %s, time: %sms]%n", sendBytes,
                (System.currentTimeMillis() - start));
    }

    @Test
    public void NioServer() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        int rcvBytes = 0;
        while (true) {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                for (; ; ) {
                    int read = socketChannel.read(buffer);
                    if (read == eof) {
                        break;
                    }
                    rcvBytes += read;
                    buffer.rewind();
                }
                System.out.printf("[rcvBytes: %s, from client %s]%n", rcvBytes, socketChannel.getRemoteAddress());
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    @Test
    public void NioClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long start = System.currentTimeMillis();
        long send;
        long sendBytes = 0;
        long size = fileChannel.size();
        // TODO
        while (sendBytes < size) {
            send = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
            sendBytes += send;
        }

        System.out.printf("[sendBytes: %s, time: %sms]%n", sendBytes,
                (System.currentTimeMillis() - start));
        fileChannel.close();
        socketChannel.close();
    }
}

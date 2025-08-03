package io.nio;

import org.junit.Test;
import cn.qingweico.constants.Constants;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * --------------- 零拷贝 ---------------
 * 普通拷贝 : 磁盘 --> 内核缓冲区 --->[CPU] --> 用户空间缓冲区 --> [用户调用write] --> 内核的Socket Buffer --> 网卡缓冲区 --> 目标服务器
 * 零拷贝 : 通过 DMA 技术 把文件内容复制到内核空间的Reader Buffer, 然后把包含数据位置和长度信息的文件描述符加载到Socket Buffer里面
 * DMA 引擎可以直接把数据从内核空间传递到网卡设配(零拷贝只是减少了不必要的拷贝次数并不是不需要数据拷贝)
 *
 * @author zqw
 * @date 2022/1/27
 */
public class ZeroCopyTest {

    private final String fileName = "";
    private final String host = Constants.LOOP_BACK;
    private final int port = Constants.DEFAULT_COMMON_PORT;
    private final int bufferSize = Constants.KB;
    private final int eof = Constants.EOF;

    @Test
    public void ioServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
    }

    @Test
    public void ioClient() throws IOException {
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
                System.out.println("client io error: " + e.getMessage());
            }
        }
        os.close();
        fis.close();
        socket.close();
        System.out.printf("[sendBytes: %s, time: %sms]%n", sendBytes, (System.currentTimeMillis() - start));
    }

    @Test
    public void nioServer() throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(inetSocketAddress);
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("pic.png"));
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
                    bos.write(buffer.array());
                    buffer.rewind();
                }
                System.out.printf("[rcvBytes: %s, from client %s]%n", rcvBytes, socketChannel.getRemoteAddress());
            } catch (IOException e) {
                System.err.println(e.getMessage());
                serverSocketChannel.close();
                break;
            }
        }
    }

    @Test
    public void nioClient() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        try (FileInputStream fis = new FileInputStream(fileName)) {
            FileChannel fileChannel = fis.getChannel();
            long start = System.currentTimeMillis();
            long send;
            long sendBytes = 0;
            long size = fileChannel.size();
            // TODO
            while (sendBytes < size) {
                // transferTo() 方法底层使用了零拷贝(sendfile()方法)
                // VM: jdk.nio.enableFastFileTransfer 解除在window下一次传输8M限制
                send = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
                sendBytes += send;
            }
            System.out.printf("[sendBytes: %s, time: %sms]%n", sendBytes, (System.currentTimeMillis() - start));
            fileChannel.close();
            socketChannel.close();
        }
    }
}

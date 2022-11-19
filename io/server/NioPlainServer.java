package io.server;

import lombok.extern.slf4j.Slf4j;
import util.constants.Constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * --------------------------------------- 基于非阻塞IO线程模型 ---------------------------------------
 * {@since jdk1.4 引入了nio [new io or non-blocking io]}
 * 基于阻塞IO线程模型(线程池) {@link BioPlainServer} 单线程 {@link SingleThreadServer}
 * 基于IO多路复用线程模型 {@link NioSelectorServer}
 * 基于AIO线程模型 {@link AioPlainServer}
 * 说明:
 * IO 线程模型
 * 阻塞IO: 使用 read() 会阻塞; 直到有数据才返回
 * 非阻塞IO: 在使用 read() 可能有数据返回也有可能读不到数据
 * 基于IO多路复用: 注册多个Channel到selector中,虽然select方法会阻塞,但是多个channel方法不会阻塞
 * 基于 epoll 模型的多路复用与基于 AIO 的不同: 多路复用并不是真正的非阻塞,在操作系统层面仍然会阻塞,
 * 当把数据从网络或者内存磁盘读到内核空间时,应用程序需等待数据从内核空间读取到用户空间中,而 AIO 可以做到
 * 真正的非阻塞,当把数据从内核拷贝到用户空间后通过异步回调函数通知用户进程
 *
 * @author zqw
 * @date 2022/2/25
 */
@Slf4j
class NioPlainServer {

    public static void main(String[] args) throws IOException {
        List<SocketChannel> channelList = new ArrayList<>();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(Constants.LOOP_BACK, Constants.NUM_8848));
        channel.configureBlocking(false);
        log.info("server start: {}", channel.getLocalAddress());
        while (true) {
            try {
                SocketChannel socketChannel = channel.accept();
                if (socketChannel != null) {
                    log.info("accept connection: {}", socketChannel.getRemoteAddress());
                    socketChannel.configureBlocking(false);
                    channelList.add(socketChannel);
                }
                Iterator<SocketChannel> iterator = channelList.iterator();
                // 存在的问题: 当连接的客户端中只有很少一部分存在读写数据时, 每次都要从客户端链表中从头遍历
                while (iterator.hasNext()) {
                    SocketChannel sc = iterator.next();
                    ByteBuffer buffer = ByteBuffer.allocate(Constants.KB);
                    sc.configureBlocking(false);
                    int read = sc.read(buffer);
                    if (read > 0) {
                        log.info(new String(buffer.array(), 0, read));
                    } else if (read == -1) {
                        iterator.remove();
                        sc.close();
                        log.warn("client disconnection: {}", sc.getRemoteAddress());
                    }
                }
            } catch (IOException e) {
                log.error("{}", e.getMessage());
                channel.close();
                break;
            }
        }
    }
}

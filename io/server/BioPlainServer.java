package io.server;

import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * -------------------------------- 基于 BIO 线程模型 --------------------------------
 *
 * @author zqw
 * @date 2021/10/18
 */
class BioPlainServer {


    // 使用线程池的缺点: 限制了客户端的并发数, 且由于服务端等待客户端读写数据时是阻塞的
    // 当客户端读写数据不是很频繁时, 会造成大量线程资源浪费


    private static final ExecutorService POOL = ThreadObjectPool.newFixedThreadPool(10);
    private static final int PORT = Constants.DEFAULT_COMMON_PORT;

    public static void serve() throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Listening for connection on port: " + server.getLocalPort());
            while (true) {
                // Block
                try {
                    final Socket client = server.accept();
                    System.out.println("client " + client.getPort() + " connected...");
                    POOL.execute(() -> {
                        InputStream in;
                        try {
                            in = client.getInputStream();
                            byte[] bytes = new byte[Constants.KB];
                            int read;
                            while ((read = in.read(bytes)) != -1) {
                                System.out.print("client " + client.getPort() + " send: ");
                                System.out.println(new String(bytes, 0, read));
                            }
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        serve();
    }
}

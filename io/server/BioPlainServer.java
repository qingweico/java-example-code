package io.server;

import thread.pool.CustomThreadPool;
import util.Constants;

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
public class BioPlainServer {

    private static final ExecutorService POOL = CustomThreadPool.newFixedThreadPool(10);
    private static final int PORT = Constants.QOMOLANGMA;

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

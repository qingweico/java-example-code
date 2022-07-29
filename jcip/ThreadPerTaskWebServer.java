package jcip;

import annotation.Pass;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Web server that starts a new thread for each request
 *
 * @author zqw
 * @date 2021/4/6
 */
@Pass
@SuppressWarnings("all")
class ThreadPerTaskWebServer {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            new Thread(task).start();
        }

    }

    private static void handleRequest(Socket connection) {
        // request-handling logic here
        System.out.println(connection.getInetAddress());
    }
}

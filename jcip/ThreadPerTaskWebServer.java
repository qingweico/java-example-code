package jcip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Web server that starts a new thread for each request
 *
 * @author:qiming
 * @date: 2021/4/6
 */
public class ThreadPerTaskWebServer {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while(true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }

    }
    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}

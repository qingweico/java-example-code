package jcip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Sequential web server
 *
 * @author zqw
 * @date 2021/4/6
 */
class SingleThreadWebServer {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }
    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}

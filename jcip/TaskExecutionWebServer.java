package jcip;

import thread.pool.ThreadPoolBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

/**
 * Web server based on thread pool
 *
 * @author zqw
 * @date 2021/4/6
 */
class TaskExecutionWebServer {
    private static final int N_THREADS = 100;
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
       final Executor pool = ThreadPoolBuilder.custom().maxPoolSize(N_THREADS).builder();
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            pool.execute(task);
        }


    }
    private static void handleRequest(Socket connection) {
        System.out.println(connection.getInetAddress());
        // request-handling logic here
    }
}

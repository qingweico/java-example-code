package jcip;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author:qiming
 * @date: 2021/4/6
 */
public class LifecycleWebServer {
    private final ExecutorService exec = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (!exec.isShutdown()) {
            try {
                final Socket coon = socket.accept();
                exec.execute(() -> handleRequest(coon));
            }catch (RejectedExecutionException e) {
                log(e);
            }
        }
    }
    private void log(Exception e) {
        Logger.getAnonymousLogger().log(Level.WARNING, "task submission rejected", e);
    }
    void handleRequest(Socket connection) {
        // request-handling logic here
    }
    interface Request {
    }
    private Request readRequest(Socket s) {
        return null;
    }

    private void dispatchRequest(Request r) {
    }

    private boolean isShutdownRequest(Request r) {
        return false;
    }
}

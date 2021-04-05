package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/22
 */
public class SocketProxy {
    static final int port = 8002;

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        final ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(port);
        print("proxy server start at " + sdf.format(new Date()));
        print("listening port: " + port);


        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                socket.setKeepAlive(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

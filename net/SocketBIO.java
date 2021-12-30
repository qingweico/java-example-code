package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author:qiming
 * @date: 2021/6/10
 */
public class SocketBIO {
    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090, 10);
        System.out.println(server.getInetAddress().getHostName() + ":" + server.getLocalPort() + " start...");
        while (true) {
            // Block
            final Socket client = server.accept();
            System.out.println("client " + client.getPort() + " connected...");
            new Thread(() -> {
                InputStream in;
                try {
                    in = client.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                    while (true) {
                        // Block
                        String response = buffer.readLine();
                        if (response != null) {
                            System.out.println(response);
                        } else {
                            client.close();
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
        }
    }
}

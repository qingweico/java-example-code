package net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author qiming
 * @date 2021/6/10
 */
public class SocketBIO {

    final static ExecutorService exec = Executors.newCachedThreadPool();

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090, 10);
        System.out.println(server.getInetAddress().getHostName() + ":" + server.getLocalPort() + " start...");
        while (true) {
            // Block
            final Socket client = server.accept();
            System.out.println("client " + client.getPort() + " connected...");
            exec.execute(() -> {
                InputStream in;
                try {
                    in = client.getInputStream();
                    byte[] bytes = new byte[1024];
                    int read;
                    //BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
                    while ((read = in.read(bytes)) != -1) {
                        System.out.print("client " + client.getPort() + " send: ");
                        System.out.println(new String(bytes, 0, read));
                        // Block
//                        System.out.println(buffer.readLine());
//                        String response = buffer.readLine();
//                        System.out.println(response);
//                        if (response != null) {
//                            System.out.println(response);
//                        } else {
//                            client.close();
//                        }

                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
        }
    }
}

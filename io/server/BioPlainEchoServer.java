package io.server;

import org.junit.Test;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2021/10/18
 */
public class BioPlainEchoServer {

    private static final ExecutorService POOL = CustomThreadPool.newFixedThreadPool(2,
            3,
            4);
    private static final int PORT = Constants.QOMOLANGMA;

    public static void serve() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
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
//                    try (BufferedReader bufferReader = new BufferedReader(
//                            new InputStreamReader(client.getInputStream()))) {
//                        PrintStream pw = new PrintStream(System.out, true);
//                        String context;
//                        while ((context = bufferReader.readLine()) != null) {
//                            pw.print(context);
//                            pw.flush();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }


    public static void main(String[] args) throws IOException {
        serve();
    }
    @Test
    public void client() throws IOException {
        Socket client = new Socket("127.0.0.1",8848);
        OutputStream outputStream = client.getOutputStream();
        String msg = "我是客户端";
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(msg);
        bufferedWriter.close();
        outputStream.close();
        client.close();
    }

}

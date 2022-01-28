package io.server;

import com.github.javafaker.Faker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/10/20
 */
public class TestWebServePressure {
    public static void main(String[] args) throws IOException {
        var pool = new ForkJoinPool();
        ArrayList<String> list = new ArrayList<>();
        var faker = new Faker();
        try (var serverSocket = new ServerSocket(8080)) {
            serverSocket.setSoTimeout(100000);
            while (true) {
                var clientSocket = serverSocket.accept();
                pool.submit(() -> {
                    try {
                        // -Xms128m -Xlog:gc*
                        var resp = "HTTP/1.1 200 OK ";
                        resp += faker.shakespeare().asYouLikeItQuote();
                        var outputStream = clientSocket.getOutputStream();
                        // memory leakage
                        list.add(resp);
                        outputStream.write(resp.getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } catch (SocketTimeoutException e) {
                        print("socket time out!");
                        System.exit(-1);
                    } catch (IOException e) {
                        e.printStackTrace(System.out);
                        System.out.println(list);
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}

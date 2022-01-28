package io.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zqw
 * @date 2021/10/18
 */
public class BioPlainEchoServer {

    public void serve(int port) throws IOException {
        final ServerSocket socketServer = new ServerSocket(port);
        while (true) {
            try {
                final Socket clientSocket = socketServer.accept();
                System.out.println("Accepting connection from: " + clientSocket.getRemoteSocketAddress());
                new Thread(echo(clientSocket)).start();
            }catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void improvedServe(int port) throws IOException {
        final ServerSocket socketServer = new ServerSocket(port);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        while (true) {
            final Socket clientSocket = socketServer.accept();
            System.out.println("Accepting connection from: " + clientSocket);
            exec.execute(echo(clientSocket));
        }
    }

    private Runnable echo(Socket clientSocket) {
        return () -> {
            try (BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()))) {
                PrintStream pw = new PrintStream(clientSocket.getOutputStream(), true);
                while (true) {
                    pw.print(bufferReader.readLine());
                    pw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        };
    }

    public static void main(String[] args) throws IOException {
        BioPlainEchoServer server = new BioPlainEchoServer();
        server.serve(8080);
    }
}

package io.server;

import util.Print;

import java.io.*;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

/**
 * -------------------------------- BIO 原生  HTTP 服务 --------------------------------
 * @author zqw
 * @date 2022/11/15
 */
class RawHttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        serverSocket.setSoTimeout(60 * 1000);
        while (true) {
            try {
                var socket = serverSocket.accept();
                Print.grace(socket.getPort(), "connected");
                var ins = new DataInputStream(socket.getInputStream());
                var bufIn = new BufferedReader(new InputStreamReader(ins));

                var requestBuilder = new StringBuilder();

                String line;

                while (!(line = bufIn.readLine()).isBlank()) {
                    requestBuilder.append(line);
                    requestBuilder.append("\n");
                }

                var request = requestBuilder.toString();
                System.out.println(request);

                var bufOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bufOut.write("HTTP/1.1 200 OK\n\n Hi,Hi,Hi,Hi");
                bufOut.flush();
                socket.close();
            } catch (SocketTimeoutException e) {
                System.out.println("Socket Time Out!");
                break;
            }
        }
    }
}

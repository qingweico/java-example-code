package coretech2.network.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author zqw
 * @date 2022/8/6
 */
class EchoServer {
    public static void main(String[] args) throws IOException {
        try(var server = new ServerSocket(8000)) {
            try(Socket socket = server.accept()) {
                InputStream inStream = socket.getInputStream();
                OutputStream outStream = socket.getOutputStream();
                try(var in = new Scanner(inStream, StandardCharsets.UTF_8)) {
                    var out = new PrintWriter(new OutputStreamWriter(outStream,
                            StandardCharsets.UTF_8), true);
                    out.println("Hello! Enter BYE to exit");
                    // echo client input
                    var done = false;
                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                        if("BYE".equals(line.trim())) {
                            done = true;
                        }
                    }
                }
            }
            // telnet localhost 8000
        }
    }
}

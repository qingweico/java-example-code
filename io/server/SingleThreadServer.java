package io.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static util.Print.print;

/**
 * @author zqw
 * @date 2019/12/10
 */
public class SingleThreadServer extends Thread {
    private final ServerSocket serverSocket;

    public SingleThreadServer(int port) throws IOException {
        // 端口为0 则表示自动绑定一个空闲端口
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }

    @Override
    public void run() {
        while (true) {
            try {
                print("Waiting for remote connection..., the port number is " + serverSocket.getLocalPort());
                Socket server = serverSocket.accept();
                print("The remote host address: " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                print(in.readUTF());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thanks for connecting me! " + server.getLocalSocketAddress() + "\nGoodBye!");
                server.close();
            } catch (SocketTimeoutException e) {
                print("Socket Time Out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt("8080");
        try {
            Thread t = new SingleThreadServer(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

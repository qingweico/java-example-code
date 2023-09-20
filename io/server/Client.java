package io.server;

import util.Print;

import java.io.*;
import java.net.Socket;

/**
 * @author zqw
 * @date 2019/12/10
 */
class Client {
    public static void main(String[] args) {
        String serveName = "localhost";
        int port = Integer.parseInt("8080");
        try {
            System.out.println("Connect to " + serveName + ":" + port);
            Socket client = new Socket(serveName, port);
            Print.println("Remote host address: " + client.getRemoteSocketAddress());
            OutputStream outToServe = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServe);
            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServe = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServe);
            Print.println("Server response: " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

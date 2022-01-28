package net;

import java.io.*;
import java.net.Socket;

import static util.Print.print;
/**
 * @author zqw
 * @date 2019/12/10
 */
public class Client {
    public static void main(String[] args) {
        String serveName = "localhost";
        int port = Integer.parseInt("8080");
        try {
            System.out.println("Connect to " + serveName + ":" + port);
            Socket client = new Socket(serveName, port);
            print("Remote host address: " + client.getRemoteSocketAddress());
            OutputStream outToServe = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServe);
            out.writeUTF("Hello from " + client.getLocalSocketAddress());
            InputStream inFromServe = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServe);
            print("Server response: " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

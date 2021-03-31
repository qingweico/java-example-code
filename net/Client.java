package net;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String serveName = "localhost";
        int port = Integer.parseInt("8080");
        try {
            System.out.println("Connect to host " + serveName + "-----" + "port: " + port);
            Socket client = new Socket(serveName, port);
            System.out.println("Remote host address: " + client.getRemoteSocketAddress());
            OutputStream outToServe = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServe);
            out.writeUTF("Hello from" + client.getLocalSocketAddress());
            InputStream inFromServe = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServe);
            System.out.println("Server response: " + in.readUTF());
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

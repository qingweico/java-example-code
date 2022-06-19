package coretech2.network;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * telnet time-a.nist.gov 13
 * This program makes a socket connection to the atomic clock in Boulder, Colorado, and prints
 * the time that the server sends.
 * @author zqw
 * @date 2022/6/18
 */
public class SimpleSocket {
    public static void main(String[] args) throws IOException {
        // 打开一个套接字, 若连接失败则抛出UnknownHostException, 存在其他问题则抛出IOException
        // 会一直无限期阻塞下去, 直到建立了连接 如果希望用户可以在任何时刻都可以中断连接 {@link #SocketTimeout}
        try (var s = new Socket("time-a.nist.gov", 13);
             var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8)) {
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}

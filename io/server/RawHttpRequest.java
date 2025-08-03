package io.server;

import cn.qingweico.concurrent.UnsafeSupport;
import cn.qingweico.io.Print;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * -------------------------------- BIO 原生  HTTP 请求 --------------------------------
 *
 * @author zqw
 * @date 2023/9/30
 */
public class RawHttpRequest {
    public static void main(String[] args) {
        try (Socket client = new Socket();) {
            client.connect(new InetSocketAddress("www.baidu.com", 80));
            OutputStream out = client.getOutputStream();
            out.write("GET / HTTP/1.0\n\n".getBytes());
            out.flush();
            UnsafeSupport.shortWait(1000);
            InputStream in = client.getInputStream();
            byte[] buf = new byte[1024];
            int size;
            while ((size = in.read(buf)) > 0) {
                System.out.println(new String(buf, 0, size));
            }
            System.out.println("\033[31m-----------------end of response-----------------\033[0m");
            System.out.println("\033[32m-----------------end of response-----------------\033[0m");
            System.out.println("\033[33m-----------------end of response-----------------\033[0m");
            System.out.println("\033[34m-----------------end of response-----------------\033[0m");
            System.out.println("\033[35m-----------------end of response-----------------\033[0m");
            System.out.println("\033[36m-----------------end of response-----------------\033[0m");
            System.out.println("\033[37m-----------------end of response-----------------\033[0m");
        } catch (Exception e) {
            Print.err("connect error", e);
        }
    }
}

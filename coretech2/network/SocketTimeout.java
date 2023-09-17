package coretech2.network;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * 套接字超时
 *
 * @author zqw
 * @date 2022/6/18
 */
@Slf4j
public class SocketTimeout {
    // TODO HOST不可以加https协议 识别不了?

    private final static String HOST = "qingweico.cn";
    private final static Integer PORT = 443;
    private final static Integer TIME_OUT = 3_000;
    public static void main(String[] args) throws IOException {
       try(var s = new Socket()) {
           // 在有可用数据返回之前 该操作将会被阻塞
           s.connect(new InetSocketAddress(HOST, PORT), TIME_OUT);
           log.info("socket is connected: {}", s.isConnected());
           log.info("socket is closed: {}", s.isClosed());
       }catch (SocketTimeoutException e) {
           log.warn("connecting time out...");

       }
        timeoutSocket();
    }
    public static void timeoutSocket() {
       try(var s = new Socket(HOST, PORT)) {
           s.setSoTimeout(TIME_OUT);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}

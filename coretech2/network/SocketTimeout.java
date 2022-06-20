package coretech2.network;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;


/**
 * @author zqw
 * @date 2022/6/18
 */
@Slf4j
public class SocketTimeout {
    private final static String HOST = "119.29.35.129";
    private final static Integer PORT = 80;
    private final static Integer TIME_OUT = 3_000;
    public static void main(String[] args) throws IOException {
       try( var s = new Socket()) {
           s.connect(new InetSocketAddress(HOST, PORT), TIME_OUT);
       }catch (SocketTimeoutException e) {
           log.warn("connecting time out...");
       }
        timeoutSocket();
    }
    public static void timeoutSocket() {
       try( var s = new Socket(HOST, PORT)) {
           s.setSoTimeout(TIME_OUT);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
}

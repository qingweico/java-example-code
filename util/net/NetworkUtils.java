package util.net;

import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Arrays;

/**
 * @author zqw
 * @date 2022/6/25
 */
@Slf4j
public class NetworkUtils {
    public static String getLocalMac(InetAddress ia) throws SocketException {
        byte[] macAddress = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        log.info("Mac Array: [{}], Mac Byte Array Length: {}", Arrays.toString(macAddress), macAddress.length);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < macAddress.length; i++) {
            if (i != 0) {
                result.append("-");
            }
            int tmp = macAddress[i] & 0xff;
            String str = Integer.toHexString(tmp);
            log.info("每8位: {}", str);
            if (str.length() == 1) {
                result.append("0").append(str);
            } else {
                result.append(str);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws UnknownHostException, SocketException {
        log.info("Mac Address: {}", getLocalMac(Inet4Address.getLocalHost()));
    }
}

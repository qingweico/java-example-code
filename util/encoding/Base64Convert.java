package util.encoding;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zqw
 * @date 2022/8/27
 */
public class Base64Convert {

    private static final Base64 BASE64 = new Base64();


    /**
     * 流转换为字符串
     *
     * @param in InputStream
     * @return String
     * @throws IOException throws {@link IOException}
     */
    public static String ioToBase64(InputStream in) throws IOException {
        String strBase64;
        try {
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            strBase64 = BASE64.encodeToString(bytes);
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return strBase64;
    }

    /**
     * 流转换为字符串
     *
     * @param bytes byte 数组
     * @return String
     */
    public static String byteToBase64(byte[] bytes) {
        String strBase64;
        // 将字节流数组转换为字符串
        strBase64 = BASE64.encodeToString(bytes);
        return strBase64;
    }

    /**
     * 将 base64 转为字节数组
     *
     * @param strBase64 base64 字符串
     * @return 节数组
     */
    public static byte[] base64ToByte(String strBase64) {
        // 解码,然后将字节转换为文件
        return BASE64.decode(strBase64);
    }
}

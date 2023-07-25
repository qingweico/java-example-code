package tools.encrypt;

import java.util.Base64;

/**
 * (JDK8+)使用 util 中的 Base64 代替 sun 包下的 BASE64Decoder
 * @link Jdk8 sun.misc.BASE64Decoder;
 * @link Jdk8 sun.misc.BASE64Encoder;
 * @author zqw
 * @date 2023/7/24
 */
class Base64Codec {
    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode("Base64Codec".getBytes());
        System.out.println(new String(encoded));
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(encoded);
        System.out.println(new String(decoded));
    }
}

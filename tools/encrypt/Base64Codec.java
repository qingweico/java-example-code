package tools.encrypt;

import java.util.Base64;

/**
 * Base64 是一种用 64 个可打印字符来表示二进制数据的编码方式(不是加密只是编码)
 * 分组 => 3字节(3 x 8 = 24) => 4字符 (4 x 6), 如果转换为二进制后数据长度不是3的倍数,
 * 使用0补齐,然后将每个6位数字映射到字符集对应的字符,后面使用0填充的每6位数字,使用一个 = 填充,
 * 编码后数据体积增加约33%, 其中不直接处理字节的符号, 而是处理字节的二进制值, 将字节通过 & 0xFF
 * 统统转换为无符号整数
 * (JDK8+)使用 util 中的 Base64 代替 sun 包下的 BASE64Decoder
 * {@code JDK8 sun.misc.BASE64Decoder}
 * {@code JDK8 sun.misc.BASE64Encoder}
 * @see jodd.util.Base64#encodeToString(String)
 * @see jodd.util.Base64#decodeToString(String)
 * @author zqw
 * @date 2023/7/24
 */
class Base64Codec {
    public static void main(String[] args) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encoded = encoder.encode("\uD83D\uDE02".getBytes());
        System.out.println(new String(encoded));
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(encoded);
        System.out.println(new String(decoded));
    }
}

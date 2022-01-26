package eight;

import java.util.Base64;

/**
 * @author zqw
 * @date 2021/10/22
 */
public class Base64Clazz {
    public static void main(String[] args) {
        Base64.Decoder decode = Base64.getDecoder();
        Base64.Encoder encoder = Base64.getEncoder();
        String encoderString = encoder.encodeToString(new byte[]{'b', 'a', 's', 'e', '6', '4'});
        System.out.println(encoderString);
        String decodeString = new String(decode.decode(encoderString));
        System.out.println(decodeString);
    }
}

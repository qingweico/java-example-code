package oak.base;

import cn.qingweico.io.Print;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * @author zqw
 * @date 2022/12/24
 */
class Md5Encryption {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String[] content = {
                "0", "1", "2", "3",
                "4", "5", "6", "7",
                "8", "9", "a", "b",
                "c", "d", "e", "f"
        };
        Scanner input = new Scanner(System.in);
        String plainText = input.nextLine();
        StringBuilder cipherText = new StringBuilder();
        byte[] digest = md5.digest(plainText.getBytes(StandardCharsets.UTF_8));
        for (byte bytes : digest) {
            int bt = bytes;
            if (bt < 0) {
                bt += 256;
            }
            int firstIndex = bt / 16;
            int secondIndex = bt % 16;
            cipherText.append(content[firstIndex]).append(content[secondIndex]);
        }
        Print.grace("plainText", plainText);
        Print.grace("cipherText", cipherText);
    }
}

package coretech2.secure.encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES 数据加密标准 是一个密钥长度为56位的古老的分组密码
 *
 * @author zqw
 * @date 2022/8/4
 */
@Slf4j
class Des {
    static String path = "coretech2/secure/encryption/";
    static String secretKey = "secret.key";
    static String plaintextFile = "plaintextFile";
    static String encryptedFile = "encryptedFile";
    static String decryptedFile = "decryptedFile";
    // ========================= 生成密钥 =========================

    public static void generateKey() throws Exception {
        // 返回实现指定加密算法的KeyGenerator对象 如果未提供该算法 则抛出NoSuchAlgorithmException
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        var random = new SecureRandom();
        // 对密钥生成器进行初始化
        keyGenerator.init(random);
        // 生成一个新的密钥
        SecretKey key = keyGenerator.generateKey();
        try (var out = new ObjectOutputStream(new FileOutputStream(path + secretKey))) {
            out.writeObject(key);
        }
    }
    // ========================= 加解密 =========================

    public static void handle(int mode) throws Exception {
        FileInputStream in;
        FileOutputStream out;
        // 加密
        if (mode == Cipher.ENCRYPT_MODE) {
            in = new FileInputStream(path + plaintextFile);
            out = new FileOutputStream(path + encryptedFile);
            // 解密
        } else if (mode == Cipher.DECRYPT_MODE) {
            in = new FileInputStream(path + encryptedFile);
            out = new FileOutputStream(path + decryptedFile);
        }else {
            log.error("mode: {}", mode);
            return;
        }
        try (var keyInput = new ObjectInputStream(new FileInputStream(path + secretKey))) {
            var key = (Key) keyInput.readObject();
            // 返回指定加密算法的Cipher对象 如果未提供该算法 则抛出NoSuchAlgorithmException
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(mode, key);
            Utils.crypto(in, out, cipher);
        }
    }


    public static void main(String[] args) throws Exception {
        generateKey();
        handle(Cipher.ENCRYPT_MODE);
        handle(Cipher.DECRYPT_MODE);
    }
}

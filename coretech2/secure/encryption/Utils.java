package coretech2.secure.encryption;


import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

/**
 * @author zqw
 * @date 2022/8/6
 */
class Utils {


    /**
     * Uses a cipher to transform the bytes in an input stream and sends the transformed bytes
     * to an output stream.
     *
     * @param in     the input stream
     * @param out    the output stream
     * @param cipher the cipher that transforms the bytes
     * @throws IOException              IOException
     * @throws GeneralSecurityException 通用的安全异常类
     */
    public static void crypto(InputStream in, OutputStream out, Cipher cipher) throws IOException, GeneralSecurityException {
     // 返回密码块的大小
     int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        var inBytes = new byte[blockSize];
        var outBytes = new byte[outputSize];

        int inLength = 0;
        var done = false;
        while (!done) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            } else {
                done = true;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        } else {
            outBytes = cipher.doFinal();
        }
        out.write(outBytes);
    }
}

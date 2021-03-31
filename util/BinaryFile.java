package util;


import java.io.*;
import java.util.Arrays;

/**
 * Utility for reading files in binary form
 *
 * @author:qiming
 * @date: 2021/2/4
 */
public class BinaryFile {
    @SuppressWarnings("all")
    public static byte[] read(File bFile) throws IOException {
        BufferedInputStream bf = new BufferedInputStream(new FileInputStream(bFile));
        try {
            byte[] data = new byte[bf.available()];
            bf.read(data);
            return data;
        } finally {
            bf.close();
        }
    }

    public static byte[] read(String bFile) throws IOException {
        return read(new File(bFile).getAbsoluteFile());
    }
}

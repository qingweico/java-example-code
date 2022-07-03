package util;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * Utility for reading files in binary form
 *
 * @author zqw
 * @date 2021/2/4
 */
@Slf4j
public class BinaryFile {

    public static byte[] read(File bFile) throws IOException {
        try (BufferedInputStream bf = new BufferedInputStream(new FileInputStream(bFile))) {
            byte[] data = new byte[bf.available()];
            int read = bf.read(data);
            log.info("file size: {}", read);
            return data;
        }
    }

    public static byte[] read(String bFile) throws IOException {
        return read(new File(bFile).getAbsoluteFile());
    }
}

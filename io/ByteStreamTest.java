package io;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import util.Constants;

import java.io.*;

/**
 * Read and write files with byte stream.
 *
 * @author zqw
 * @date 2020/04/23
 */
public class ByteStreamTest {
    private final String src = "io/data.txt";
    private final String dest = "io/dest.txt";
    private long startTime = 0L;

    @BeforeMethod
    public void start() {
        startTime = System.currentTimeMillis();
    }

    @AfterMethod
    public void end() {
        long endTime = System.currentTimeMillis();
        System.out.println("time: " + (endTime - startTime) + "ms");
    }

    @Test
    public void readByte() throws IOException {
        readByte(src, dest);
    }

    @Test
    public void readByteArray() throws IOException {
        readByteArray(src, dest);
    }

    @Test
    public void readBufferByte() throws IOException {
        readBufferByte(src, dest);
    }

    @Test
    public void readBufferByteArray() throws IOException {
        readBufferByteArray(src, dest);
    }


    /**
     * Basic byte stream reads and writes one byte at a time.
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readByte(String src, String dst) throws IOException {
        FileInputStream fo = new FileInputStream(src);
        FileOutputStream fs = new FileOutputStream(dst);
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fs.close();
        fo.close();

    }

    /**
     * Basic byte stream reads and writes one byte array at a time.
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readByteArray(String src, String dst) throws IOException {
        FileInputStream fo = new FileInputStream(src);
        FileOutputStream fs = new FileOutputStream(dst);
        byte[] bys = new byte[Constants.KB];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fs.close();
        fo.close();

    }

    /**
     * Efficient byte stream reads and writes one byte at a time.
     * <p>
     * Java built-in array cache
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readBufferByte(String src, String dst) throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream(dst));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fs.close();
        fo.close();
    }

    /**
     * Efficient byte stream reads and writes a byte.
     *
     * @throws IOException An {@code IOException} will be thrown If an I/O error occurs
     */
    public static void readBufferByteArray(String src, String dst) throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream(dst));
        byte[] bys = new byte[Constants.KB];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fs.close();
        fo.close();
    }
}

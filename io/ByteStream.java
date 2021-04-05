package io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author:qiming
 * @date: 2020/04/23
 */
public class ByteStream {
    /**
     * Four ways to read and write files with byte stream
     *
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        readByte(args[0], args[1]);
        long endTime = System.currentTimeMillis();
        System.out.println("The time it takes for the basic byte stream to read and write a byte is: " + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        readByteArray(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("The time it takes for the basic byte stream to read and write a byte array is: " + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        readBufferByte(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("The time it takes for an efficient byte stream to read and write a byte is: " + (endTime - startTime) + "ms");
        startTime = System.currentTimeMillis();
        readBufferByteArray(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("The time it takes for an efficient byte stream to read and write a byte array is: " + (endTime - startTime) + "ms");
    }

    /**
     * Basic byte stream reads and writes one byte at a time
     *
     * @throws IOException IOException
     */
    public static void readByte(String path, String dst) throws IOException {
        FileInputStream fo = new FileInputStream(path);
        FileOutputStream fs = new FileOutputStream(dst);
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();

    }

    /**
     * Basic byte stream reads and writes one byte array at a time
     *
     * @throws IOException IOException
     */
    public static void readByteArray(String path, String dst) throws IOException {
        FileInputStream fo = new FileInputStream(path);
        FileOutputStream fs = new FileOutputStream(dst);
        byte[] bys = new byte[1024];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fo.close();
        fs.close();

    }

    /**
     * Efficient byte stream reads and writes one byte at a time
     * <p>
     * Java built-in array cache
     *
     * @throws IOException IOException
     */
    public static void readBufferByte(String path, String dst) throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream(path));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream(dst));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();
    }

    /**
     * Efficient byte stream reads and writes a byte
     *
     * @throws IOException IOException
     */
    public static void readBufferByteArray(String path, String dst) throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream(path));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream(dst));
        byte[] bys = new byte[1024];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fo.close();
        fs.close();
    }
}

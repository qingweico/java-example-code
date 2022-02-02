package io;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * @author zqw
 * @date 2020/03/27
 */
public class CharacterStream {
    /**
     * Five ways to read and write files with character streams.
     *
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        readChar(args[0], args[1]);
        long endTime = System.currentTimeMillis();
        System.out.println("readChar:" + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readCharArray(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readArray: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readCharByFileReader(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readCharByFileReader: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readCharArrayByFileReader(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readCharArrayByFileReader: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readCharByBuffered(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readCharByBuffered: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readCharArrayByBuffered(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readCharArrayByBuffered: " + (endTime - startTime) + "ms");

        startTime = System.currentTimeMillis();
        readStringByBuffered(args[0], args[1]);
        endTime = System.currentTimeMillis();
        System.out.println("readStringByBuffered: " + (endTime - startTime) + "ms");

    }

    /**
     * 基本字符流读取一个字节
     *
     * @throws IOException IOException
     */
    public static void readChar(String path, String dest) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(path));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(dest));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 基本字符流读取一个字符数组
     *
     * @throws IOException IOException
     */
    public static void readCharArray(String path, String dest) throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream(path));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream(dest));
        char[] ch = new char[1024 * 1024];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 高效字符流读取一个字符
     *
     * @throws IOException IOException
     */
    public static void readCharByBuffered(String path, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(path));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 高效字符流读取一个字符数组
     *
     * @throws IOException IOException
     */
    public static void readCharArrayByBuffered(String path, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(path));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
        char[] ch = new char[1024 * 1024];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 基本字符流(FileReader)读写一个字符
     *
     * @throws IOException IOException
     */
    public static void readCharByFileReader(String path, String dest) throws IOException {
        FileReader fo = new FileReader(path);
        FileWriter fs = new FileWriter(dest);
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 基本字符流(FileReader)读写一个字符数组
     *
     * @throws IOException IOException
     */
    public static void readCharArrayByFileReader(String path, String dest) throws IOException {
        FileReader fo = new FileReader(path);
        FileWriter fs = new FileWriter(dest);
        char[] ch = new char[1024];
        int len;
        while ((len = fo.read(ch)) != -1) {
            fs.write(ch, 0, len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 高效字符流读写一个字符串
     *
     * @throws IOException IOException
     */
    public static void readStringByBuffered(String path, String dest) throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader(path));
        BufferedWriter fs = new BufferedWriter(new FileWriter(dest));
        String s;
        while ((s = fo.readLine()) != null) {
            fs.write(s);
            // fs.newLine();
            fs.flush();
        }
        fo.close();
        fs.close();
    }
}

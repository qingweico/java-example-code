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
     * 四种字节流读写文件方式
     *
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        method();
        long endTime = System.currentTimeMillis();
        System.out.println("基本字节流读写一个字节这种方式花费的时间为" + (endTime - startTime) + "ms");
        long startTimeOne = System.currentTimeMillis();
        methodOne();
        long endTimeOne = System.currentTimeMillis();
        System.out.println("基本字节流读写一个字节数组这种方式花费的时间为" + (endTimeOne - startTimeOne) + "ms");
        long startTimeTwo = System.currentTimeMillis();
        methodTwo();
        long endTimeTwo = System.currentTimeMillis();
        System.out.println("高效字节流读写一个字节这种方式花费的时间为" + (endTimeTwo - startTimeTwo) + "ms");
        long startTimeThree = System.currentTimeMillis();
        methodThree();
        long endTimeThree = System.currentTimeMillis();
        System.out.println("高效字节流读写一个字节数组这种方式花费的时间为" + (endTimeThree - startTimeThree) + "ms");
    }

    /**
     * 基本字节流一次读写一个字节
     *
     * @throws IOException IOException
     */
    public static void method() throws IOException {
        FileInputStream fo = new FileInputStream("E:\\photos\\franck-v-9HkyszvcRBY-unsplash.jpg");
        FileOutputStream fs = new FileOutputStream("E:\\1.jpg");
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();

    }

    /**
     * 基本字节流一次读写一个字节数组
     *
     * @throws IOException IOException
     */
    public static void methodOne() throws IOException {
        FileInputStream fo = new FileInputStream("E:\\photos\\franck-v-9HkyszvcRBY-unsplash.jpg");
        FileOutputStream fs = new FileOutputStream("E:\\2.jpg");
        byte[] bys = new byte[1024];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fo.close();
        fs.close();

    }

    /**
     * 高效字节流一次读写一个字节
     * <p>
     * java内置数组缓存
     *
     * @throws IOException IOException
     */
    public static void methodTwo() throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream("E:\\photos\\franck-v-9HkyszvcRBY-unsplash.jpg"));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream("E:\\3.jpg"));
        int len;
        while ((len = fo.read()) != -1) {
            fs.write(len);
        }
        fo.close();
        fs.close();
    }

    /**
     * 高效字节流读写一个字节数组
     *
     * @throws IOException IOException
     */
    public static void methodThree() throws IOException {
        BufferedInputStream fo = new BufferedInputStream(new FileInputStream("E:\\photos\\franck-v-9HkyszvcRBY-unsplash.jpg"));
        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream("E:\\4.jpg"));
        byte[] bys = new byte[1024];
        int len;
        while ((len = fo.read(bys)) != -1) {
            fs.write(bys, 0, len);
        }
        fo.close();
        fs.close();
    }
}

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
 * @author:qiming
 * @date: 2020/03/27
 */
public class CharacterStream {
    /**
     * 五种字符流读写文件方式
     *
     * @param args args
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        method();
        long endTime = System.currentTimeMillis();
        System.out.println("基本字符流读写一个字节花费的时间为" + (endTime - startTime) + "ms");
        long startTimeOne = System.currentTimeMillis();
        methodOne();
        long endTimeOne = System.currentTimeMillis();
        System.out.println("基本字符流读写一个字符数组花费的时间为" + (endTimeOne - startTimeOne) + "ms");
        long startTimeFour = System.currentTimeMillis();
        methodFour();
        long endTimeFour = System.currentTimeMillis();
        System.out.println("基本字符流(FileReader)读写一个字符花费的时间为" + (endTimeFour - startTimeFour) + "ms");
        long startTimeFive = System.currentTimeMillis();
        methodFive();
        long endTimeFive = System.currentTimeMillis();
        System.out.println("基本字符流(FileReader)读写一个字符数组花费的的时间为" + (endTimeFive - startTimeFive) + "ms");
        long startTimeTwo = System.currentTimeMillis();
        methodTwo();
        long endTimeTwo = System.currentTimeMillis();
        System.out.println("高效字符流读写一个字符花费的时间为" + (endTimeTwo - startTimeTwo) + "ms");
        long startTimeThree = System.currentTimeMillis();
        methodThree();
        long endTimeThree = System.currentTimeMillis();
        System.out.println("高效字符流读写一个字符数组花费的时间为" + (endTimeThree - startTimeThree) + "ms");
        long startTimeSix = System.currentTimeMillis();
        methodSix();
        long endTimeSix = System.currentTimeMillis();
        System.out.println("高效字符流读写一个字符串花费的时间为" + (endTimeSix - startTimeSix) + "ms");
        long startTimeSeven = System.currentTimeMillis();
        methodSeven();
        System.out.println("高效字符流读取一个字符(0.98G的电影)花费的时间为" + (System.currentTimeMillis() - startTimeSeven) + "ms");
        long startTimeEight = System.currentTimeMillis();
        methodEight();
        System.out.println("高效字符流读取一个字符数组(0.98G的电影花费的时间为" + (System.currentTimeMillis() - startTimeEight) + "ms");
    }

    /**
     * 基本字符流读取一个字节
     *
     * @throws IOException IOException
     */
    public static void method() throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream("D:\\picture\\1.jpg"));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream("X:\\5.jpg"));
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
    public static void methodOne() throws IOException {
        InputStreamReader fo = new InputStreamReader(new FileInputStream("D:\\picture\\1.jpg"));
        OutputStreamWriter fs = new OutputStreamWriter(new FileOutputStream("X:\\6.jpg"));
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
    public static void methodTwo() throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader("D:\\picture\\1.jpg"));
        BufferedWriter fs = new BufferedWriter(new FileWriter("X:\\7.jpg"));
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
    public static void methodThree() throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader("D:\\picture\\1.jpg"));
        BufferedWriter fs = new BufferedWriter(new FileWriter("X:\\8.jpg"));
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
    public static void methodFour() throws IOException {
        FileReader fo = new FileReader("D:\\picture\\1.jpg");
        FileWriter fs = new FileWriter("X:\\9.jpg");
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
    public static void methodFive() throws IOException {
        FileReader fo = new FileReader("D:\\picture\\1.jpg");
        FileWriter fs = new FileWriter("X:\\10.jpg");
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
    public static void methodSix() throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader("D:\\picture\\1.jpg"));
        BufferedWriter fs = new BufferedWriter(new FileWriter("X:\\11.jpg"));
        String s;
        while ((s = fo.readLine()) != null) {
            fs.write(s);
            // fs.newLine();
            fs.flush();
        }
        fo.close();
        fs.close();
    }

    /**
     * 高效字符流读取一个字符
     *
     * @throws IOException IOException
     */
    public static void methodSeven() throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader("E:\\ppt\\1.mp4"));
        BufferedWriter fs = new BufferedWriter(new FileWriter("X:\\1.mp4"));
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
    public static void methodEight() throws IOException {
        BufferedReader fo = new BufferedReader(new FileReader("E:\\ppt\\1.mp4"));
        BufferedWriter fs = new BufferedWriter(new FileWriter("X:\\2.mp4"));
        int len = 0;
        char[] ch = new char[1024];
        while ((fo.read()) != -1) {
            fs.write(ch, 0, len);
        }
        fo.close();
        fs.close();
    }
}

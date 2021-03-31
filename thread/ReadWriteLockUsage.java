package thread;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 *
 * @author:qiming
 * @date: 2020/12/19
 */
public class ReadWriteLockUsage {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws IOException {
        final Object o = new Object();
        // Read locks, also known as shared locks, can be held by multiple threads simultaneously.
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(() -> {
                readWriteLock.readLock().lock();
                read(finalI);
                readWriteLock.readLock().unlock();
            }, "read").start();
        }
        // Write locks, also known as exclusive locks, can only be held by one thread.
        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(() -> {
                readWriteLock.writeLock().lock();
                write(finalI);
                readWriteLock.writeLock().unlock();
            }, "write").start();
        }
    }

    // read
    public static void read(int finalI) {
        try {
            InputStream is = new FileInputStream("src/io/temp");
            System.out.println(Thread.currentThread().getName() + finalI + "线程开始读取!");
            try {
                int len = is.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + finalI + "线程读取完毕!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // write
    public static void write(int finalI) {
        try {
            OutputStream os = new FileOutputStream("src/io/temp", true);
            System.out.println(Thread.currentThread().getName() + finalI + "线程开始写入!");

            try {
                os.write(String.valueOf(finalI).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + finalI + "线程写入完毕!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

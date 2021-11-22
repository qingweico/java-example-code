package thread;

import java.io.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * read-write lock
 *
 * @author:qiming
 * @date: 2020/12/19
 */
public class RwLock {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static void main(String[] args) throws IOException {
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
            InputStream is = new FileInputStream("io/temp");
            System.out.println(Thread.currentThread().getName() + finalI + " thread starts reading!");
            try {
                System.out.println(is.read());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + finalI + " thread reading is complete!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // write
    public static void write(int finalI) {
        try {
            OutputStream os = new FileOutputStream("io/temp", true);
            System.out.println(Thread.currentThread().getName() + finalI + " thread starts writing!");

            try {
                os.write(String.valueOf(finalI).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + finalI + " thread is written!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

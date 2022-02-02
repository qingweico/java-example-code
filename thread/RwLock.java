package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * read-write lock
 *
 * @author zqw
 * @date 2020/12/19
 */
class RwLock {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 10, 5);
    static String path = "io/";
    static String fileName = "data.txt";

    public static void main(String[] args) throws IOException {
        // Read locks, also known as shared locks, can be held by multiple threads simultaneously.
        for (int i = 0; i < Constants.FIVE; i++) {
            pool.execute(() -> {
                readWriteLock.readLock().lock();
                read(path + fileName);
                readWriteLock.readLock().unlock();
            });
        }
        // Write locks, also known as exclusive locks, can only be held by one thread.
        for (int i = 0; i < Constants.FIVE; i++) {
            int finalI = i;
            pool.execute(() -> {
                readWriteLock.writeLock().lock();
                write(finalI, path + fileName);
                readWriteLock.writeLock().unlock();
            });
        }
        pool.shutdown();
    }

    public static void read(String readPath) {
        try {
            InputStream is = new FileInputStream(readPath);
            byte[] buffer = new byte[Constants.KB];
            System.out.println(Thread.currentThread().getName() + " starts reading!");
            try {
                int read;
                do {
                    read = is.read(buffer);
                } while (read != -1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " reading complete!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(int finalI, String writePath) {
        try {
            OutputStream os = new FileOutputStream(writePath, true);
            System.out.println(Thread.currentThread().getName() + " starts writing!");

            try {
                os.write(String.valueOf(finalI).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " written complete!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

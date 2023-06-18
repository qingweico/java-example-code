package thread.lock;

import thread.pool.CustomThreadPool;
import util.constants.Constants;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * read-write lock
 * RRW {@link ReentrantReadWriteLock}
 * RRW 被很好地应用在读大于写地并发场景中 但是在读取很多 写入很少的我情况下 RRW会使写入线程
 * 遭遇饥饿问题 即写入线程会因为迟迟无法竞争到锁而一直处于等待状态
 * {@since JDK1.8} 提供了 {@link StampedLock} 类解决了这个问题
 * {@link Point}
 * StampedLock 不是基于 AQS 实现的,但是实现的原理和AQS是一样的,都是基于队列和锁状态实现的,与 RRW 不同
 * 的是 StampedLock 控制锁有三种模式:写,悲观读以及乐观读,并且 StampedLock 在获取锁时会返回一个票据 stamp,
 * 获取的 stamp 除了在释放锁时需要校验,在乐观读模式下,stamp还会作为读取共享资源后的二次校验
 * @author zqw
 * @date 2020/12/19
 */
class RwLock {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10);
    static String fileName = Constants.DEFAULT_FILE_PATH_MAME;

    public static void main(String[] args) throws IOException {
        // Read locks, also known as shared locks, can be held by multiple threads simultaneously.
        for (int i = 0; i < Constants.FIVE; i++) {
            pool.execute(() -> {
                readWriteLock.readLock().lock();
                read(fileName);
                readWriteLock.readLock().unlock();
            });
        }
        // Write locks, also known as exclusive locks, can only be held by one thread.
        for (int i = 0; i < Constants.FIVE; i++) {
            int finalI = i;
            pool.execute(() -> {
                readWriteLock.writeLock().lock();
                write(finalI, fileName);
                readWriteLock.writeLock().unlock();
            });
        }
        pool.shutdown();
    }

    public static void read(String readPath) {
        try (InputStream is = new FileInputStream(readPath)) {
            byte[] buffer = new byte[Constants.KB];
            System.out.println(Thread.currentThread().getName() + " starts reading!");
            try {
                int read;
                do {
                    read = is.read(buffer);
                } while (read != -1);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " reading complete!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void write(int finalI, String writePath) {
        try (OutputStream os = new FileOutputStream(writePath, true);) {
            System.out.println(Thread.currentThread().getName() + " starts writing!");
            try {
                os.write(String.valueOf(finalI).getBytes());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName() + " written complete!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

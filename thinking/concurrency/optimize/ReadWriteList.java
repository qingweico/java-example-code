package thinking.concurrency.optimize;


import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import static util.Print.print;

/**
 * ReadWriteLock can have multiple readers at the same time, as long as none of them
 * attempt to write.
 * If the write lock is already held by another task, no reader can access it until
 * the write lock is released.
 *
 * @author:qiming
 * @date: 2021/2/2
 */
public class ReadWriteList<T> {
    private final ArrayList<T> lockedList;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ReadWriteList(int size, T initialValue) {
        lockedList = new ArrayList<>(
                Collections.nCopies(size, initialValue));
    }

    public T set(int index, T element) {
        Lock wLock = lock.writeLock();
        wLock.lock();
        try {
            return lockedList.set(index, element);
        } finally {
            wLock.unlock();
        }
    }

    public T get(int index) {
        Lock rLock = lock.readLock();
        rLock.lock();
        try {
            // Show that multiple readers may acquire the read lock.
            if (lock.getReadHoldCount() > 1) {
                print(lock.getReadHoldCount());
            }
            return lockedList.get(index);
        } finally {
            rLock.unlock();
        }
    }

    public static void main(String[] args) {
        new ReadWriteListTest(30, 1);
    }

}

class ReadWriteListTest {
    ExecutorService exec = Executors.newCachedThreadPool();
    private static final int SIZE = 100;
    private static final Random random = new Random(47);
    private final ReadWriteList<Integer> list = new ReadWriteList<>(SIZE, 0);

    private class Writer implements Runnable {

        @Override
        public void run() {
            try {
                for (int i = 0; i < 20; i++) {
                    list.set(i, random.nextInt(100));
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            } catch (InterruptedException e) {
                // Acceptable way to way
            }
            print("Writer finished, shutting down");
            exec.shutdownNow();
        }
    }

    private class Reader implements Runnable {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    for (int i = 0; i < SIZE; i++) {
                        int val = list.get(i);
                        TimeUnit.MILLISECONDS.sleep(1);
                        print(val);
                    }
                }
            } catch (InterruptedException e) {
                // Acceptable way to way
            }
        }
    }

    public ReadWriteListTest(int readers, int writers) {
        for (int i = 0; i < readers; i++) {
            exec.execute(new Reader());
        }
        for (int i = 0; i < writers; i++) {
            exec.execute(new Writer());
        }
    }
}

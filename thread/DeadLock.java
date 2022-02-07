package thread;

import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zqw
 * @date 2021/10/17
 */
class DeadLock {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 1);

    static class Friend {
        private final String name;
        private final ReentrantLock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void bow(Friend bow) {
            lock.lock();
            try {
                System.out.format("%s: %s bow to me!\n", this.name, bow.getName());
                // Deadlock! Didn't release the lock, you should firstly
                // release the lock and then release the resource.
                bow.release(this);
            } finally {
                lock.unlock();
            }

        }

        public void release(Friend bow) {
            lock.lock();
            try {
                System.out.format("%s: %s return me!\n", this.name, bow.getName());
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        final Friend hong = new Friend("hong");
        final Friend lan = new Friend("lan");
        pool.execute(() -> hong.bow(lan));
        pool.execute(() -> lan.bow(hong));
        pool.shutdown();
    }
}

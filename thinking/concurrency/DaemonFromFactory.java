package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * @author zqw
 * @date 201/1/16
 */
class DaemonFromFactory implements Runnable {

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                TimeUnit.MICROSECONDS.sleep(100);
                print(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            print("Interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        ExecutorService pool = ThreadPoolBuilder.custom().threadFactory(new DaemonThreadFactory()).builder();
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new DaemonFromFactory());
        }
        print("All daemon started");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}


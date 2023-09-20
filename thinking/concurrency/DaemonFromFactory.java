package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

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
                Print.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            Print.println("Interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        ExecutorService pool = ThreadPoolBuilder.builder().threadFactory(new DaemonThreadFactory()).build();
        for (int i = 0; i < threadCount; i++) {
            pool.execute(new DaemonFromFactory());
        }
        Print.println("All daemon started");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}


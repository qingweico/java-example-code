package thinking.concurrency.juc;


import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * It is used to synchronize one or more tasks, forcing them to wait for the completion
 * of a set of operations performed by another task.
 *
 * @author:qiming
 * @date: 2021/2/1
 */

public class CountDownLatchUsage {
    static final int SIZE = 100;

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++) {
            exec.execute(new WaitingTask(countDownLatch));
        }
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new TaskPortion(countDownLatch));
        }
        print("Launched all tasks");
        // Quit when all tasks complete
        exec.shutdown();
    }
}

// Performs some portion of a task:
class TaskPortion implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private static final Random random = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
            // Decrease this count
            latch.countDown();
        } catch (InterruptedException ex) {
            // Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(2000));
        print(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

// Waiting on the CountDownLatch:
class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Any call to await() method on this object(latch) will block until the
            // count reaches 0.
            latch.await();
            print("Latched barrier pass for " + this);
        } catch (InterruptedException ex) {
            print(this + " interrupted");
        }
    }

    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);

    }
}


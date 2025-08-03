package thinking.concurrency.juc;


import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch:
 * It is used to synchronize one or more tasks, forcing them to wait for the completion
 * of a set of operations performed by another task.
 *
 * @author zqw
 * @date 2021/2/1
 */

class Cdl {
    static final int THREAD_COUNT = 100;
    static final int WAITING_TASK_COUNT = 10;

    public static void main(String[] args) {
        ExecutorService exec = ThreadObjectPool.newFixedThreadPool(THREAD_COUNT);
        // All must share a single CountDownLatch object
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        for (int i = 0; i < WAITING_TASK_COUNT; i++) {
            exec.execute(new WaitingTask(countDownLatch));
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            exec.execute(new TaskPortion(countDownLatch));
        }
        Print.println("Launched all tasks");
        // Quit when all tasks complete
        exec.shutdown();
    }
}

/**
 * Performs some portion of a task
 */
class TaskPortion implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private static final Random R = new Random(47);
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            doWork();
        } catch (InterruptedException ex) {
            // Acceptable way to exit
        } finally {
            // Decrease this count
            latch.countDown();
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(R.nextInt(2000));
        Print.println(this + "completed");
    }

    @Override
    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

/**
 * Waiting on the CountDownLatch:
 */
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
            Print.println("Latched barrier pass for " + this);
        } catch (InterruptedException ex) {
            Print.println(this + " interrupted");
        }
    }

    @Override
    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);

    }
}


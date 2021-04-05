package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2020/11/30
 */
public class LiftOff implements Runnable {
    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {
    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "). ";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.print(status());
            try {
                TimeUnit.SECONDS.sleep(1);
                // Exceptions cannot be propagated across threads, so you must handle all
                // exceptions generated within the task locally.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Calling the yield method will signal to the thread scheduler that you've
            // done your job and that it's time for another thread to use the CPU, but
            // this is just a hint, and there is no mechanism to guarantee that it will
            // be adopted.
            Thread.yield();
        }
    }

}

class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff(4));
        t.start();
    }
}

class MoreBasicThread {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new LiftOff(4));
            t.start();
        }
        System.out.println("Waiting for LiftOff");
    }
}

// Using Executor
// Executor allows you to manage the execution of asynchronous tasks without explicitly managing
// cycles in a thread's declaration, and is the preferred way to start a thread.
class CachedThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff(5));
        }
        exec.shutdown();
    }
}

// FixThreadPool can limit the number of thread and solve the time of program running.
class FixedThreadPool {
    public static void main(String[] args) {
        // Constructor argument is number of threads
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}

// A SingleThreadExecutor is like a FixThreadPool with one thread.
// If multiple tasks are submitted to a SingleThreadExecutor, the tasks are queued, all the tasks
// use the same thread and all the tasks submitted to it are serialized and its own suspended task
// queue is maintained.
class SingleThreadPool {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();

    }
}

package thinking.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Demonstration of the Runnable interface
 *
 * @author zqw
 * @date 2020/11/30
 */

class LiftOff implements Runnable {
    protected int countDown;
    private static int taskCount = 0;
    private final int id = taskCount++;
    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "). ";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(Thread.currentThread().getName() + ": " + status());
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
            // be adopted, you're just suggesting that other threads of the same priority
            // can run, you shouldn't rely on yield() for any vital control or for adjust
            // applications.
            Thread.yield();
        }

    }
}

class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff(5);
        launch.run();
    }

}

class BasicThreads {
    public static void main(String[] args) {
        // 使用一个线程
    }
}

class MoreBasicThread {

    public static void main(String[] args) {
        // 创建更多的线程
    }
}

/**
 * Using Executor allows you to manage the execution of asynchronous tasks without explicitly
 * managing cycles in a thread's declaration, and is the preferred way to start a thread.
 */
class CachedThreadPool {
    public static void main(String[] args) {

    }
}

/**
 * FixThreadPool can limit the number of thread and solve the time of program running.
 */
class FixedThreadPool {
    public static void main(String[] args) {

    }
}

/**
 * A SingleThreadExecutor is like a FixThreadPool with one thread.
 * If multiple tasks are submitted to a SingleThreadExecutor, the tasks are queued, all the tasks
 * use the same thread and all the tasks submitted to it are serialized and its own suspended task
 * queue is maintained.
 */
class SingleThreadPool {
    public static void main(String[] args) {

    }
}

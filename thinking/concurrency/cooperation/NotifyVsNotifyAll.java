package thinking.concurrency.cooperation;

import annotation.Pass;
import thread.pool.CustomThreadPool;
import util.constants.Constants;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * notifyAll: Only tasks waiting for this lock are awakened.
 *
 * @author zqw
 * @date 2021/4/11
 */
@Pass
@SuppressWarnings("all")
class NotifyVsNotifyAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = CustomThreadPool.newFixedThreadPool(10);
        for (int i = 0; i < Constants.FIVE; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            boolean prod = true;

            @Override
            public void run() {
                if (prod) {
                    print("notify()");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    print("notifyAll()");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
            // Run every .4 second.
        }, 400, 400);
        // Run for while...
        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        print("Timer canceled");
        TimeUnit.MILLISECONDS.sleep(500);
        print("Task2.blocker.prodAll()");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        print("Shutting down");
        // Interrupted all tasks
        exec.shutdownNow();

    }
}

class Blocker {
    synchronized void waitingCall() {
        try {
            while (!Thread.interrupted()) {
                wait();
                print(Thread.currentThread() + " ");
            }
        } catch (InterruptedException e) {
            // OK to exit this way
        }
    }

    synchronized void prod() {
        notify();
    }

    synchronized void prodAll() {
        notifyAll();
    }
}

class Task implements Runnable {
    /**A separated Blocker object*/
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    /** A separated Blocker object*/
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}
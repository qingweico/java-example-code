package thinking.concurrency.interrupted;

import thread.pool.CustomThreadPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static util.Print.exit;
import static util.Print.print;

/**
 * Interrupting a blocked thread
 * {@link Thread#interrupt()} 仅仅将线程对象(正常状态)的中断标志设置为true,并不会停止该线程;
 * 该线程需要不断检测标识位来判断是否来中断自身, 中断不活动的线程不会产生影响
 * {@link Thread#interrupted()} 判断线程是否被中断并清除当前中断状态
 * {@link Thread#isInterrupted()} 判断线程是否被中断 {@code 都使用了Thread类中volatile类型的标志位interrupted}
 * @see SimpleInterrupt
 * @author zqw
 * @date 2021/1/31
 */
public class Interrupting {
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2);

    static void test(Runnable r) throws InterruptedException {
        Future<?> task = pool.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        print("Interrupting " + r.getClass().getName());
        // Interrupts if running
        task.cancel(true);
        print("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IoBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        print("Aborting with System.exit(0)");
        // Since last 2 interrupts failed
        exit(0);
    }
}

/**
 * Interruptible blocking
 */
class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            print("InterruptedException");
        }
        print("Exiting SleepBlocked.run()");
    }
}

/**
 * Uninterruptible block
 */
class IoBlocked implements Runnable {

    private final InputStream in;

    public IoBlocked(InputStream is) {
        in = is;
    }

    @Override
    public void run() {
        try {
            print("Waiting for read()");
            while (true) {
                int read = in.read();
                if (read == -1) {
                    break;
                }
            }
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                print("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        print("Exiting IOBlocked.run()");
    }
}

/**
 * Uninterruptible block
 */
@SuppressWarnings("InfiniteLoopStatement")
class SynchronizedBlocked implements Runnable {
    ExecutorService pool = CustomThreadPool.newFixedThreadPool(1, 1, 1);

    public synchronized void f() {
        // Never releases lock
        while (true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        // Lock acquired by this thread
        pool.execute(this::f);
    }

    @Override
    public void run() {
        print("Trying to call f()");
        f();
        print("Exiting SynchronizedBlocked.run()");
    }
}

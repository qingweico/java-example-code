package thinking.concurrency.interrupted;

import cn.qingweico.concurrent.pool.ThreadObjectPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static cn.qingweico.io.Print.*;

/**
 * ################################# Interrupting a blocked thread #################################
 * Java 中 synchronized 以获取锁地线程不可以响应外部中断 而 Lock 接口提供了 {@link Lock#lockInterruptibly()}
 * 用来响应中断,抛出异常,同时释放锁
 * Sleep 可以被中断,但是阻塞式 IO 不可以被中断
 * ================================= Java 中断 API =================================
 * {@link Thread#interrupt()} 仅仅将线程对象(正常状态)的中断标志设置为true,并不会停止该线程;
 * 该线程需要不断检测标识位来判断是否来中断自身, 中断不活动的线程不会产生影响
 * {@link Thread#interrupted()} 判断线程是否被中断并清除当前中断状态
 * {@link Thread#isInterrupted()} 判断线程是否被中断 {@code 都使用了Thread类中volatile类型的标志位interrupted}
 * interrupted方法 和 isInterrupted方法的区别: 前者用于检查当前线程的中断状态, 并且会清除该线程的中断标志, 后者用于
 * 检查当前线程的中断状态, 并且不会清除中断标志
 *
 * @author zqw
 * @date 2021/1/31
 * @see SimpleInterrupt
 */
public class Interrupting {
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(4);

    static void test(Runnable r) throws InterruptedException {
        Future<?> task = pool.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        println("Interrupting " + r.getClass().getName());
        // Interrupts if running
        task.cancel(true);
        println("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IoBlocked(System.in));
        test(new SynchronizedBlocked());
        test(new LockBlocked());
        TimeUnit.SECONDS.sleep(3);
        println("Aborting with System.exit(0)");
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
            println("InterruptedException");
        }
        println("Exiting SleepBlocked.run()");
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
            println("Waiting for read()");
            while (true) {
                int read = in.read();
                if (read == -1) {
                    break;
                }
            }
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        println("Exiting IOBlocked.run()");
    }
}

/**
 * Uninterruptible block
 */
@SuppressWarnings("InfiniteLoopStatement")
class SynchronizedBlocked implements Runnable {
    ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);

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
        System.out.println("[SynchronizedBlocked] Trying to call f()");
        f();
        System.out.println("[SynchronizedBlocked] Trying to call f()");
    }
}

/**
 * Interruptible blocking
 */
@SuppressWarnings("InfiniteLoopStatement")
class LockBlocked implements Runnable {
    ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1);
    Lock lock = new ReentrantLock();

    @SuppressWarnings("all")
    public void f() {
        try {
            // https://github.com/alibaba/p3c/issues/653
            lock.lockInterruptibly();
            // Never releases lock
            while (true) {
                Thread.yield();
            }
        } catch (InterruptedException e) {
            System.out.println("[LockBlocked] Interrupted, exit from f()");
        } finally {
            lock.unlock();
        }

    }

    public LockBlocked() {
        // Lock acquired by this thread
        pool.execute(this::f);
    }

    @Override
    public void run() {
        System.out.println("[LockBlocked] Trying to call f()");
        f();
        // cant reach!
        System.out.println("[LockBlocked] Exiting LockBlocked.run()");
    }
}

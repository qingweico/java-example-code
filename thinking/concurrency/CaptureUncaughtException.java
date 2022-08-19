package thinking.concurrency;

import thread.pool.CustomThreadPool;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import static util.Print.print;

/**
 * 线程池中捕获异常; 实现 {@link Thread.UncaughtExceptionHandler}
 *
 * @author zqw
 * @date 2021/1/18
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = CustomThreadPool.newFixedThreadPool(1);
        CustomThreadPool.buildThreadFactory(exec, new HandleThreadFactory());
        exec.execute(new ExceptionThreadCaught());
        exec.shutdown();
    }

}

class ExceptionThreadCaught implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        print("run() by " + t);
        print("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }

}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        print("caught " + e);
    }
}

class HandleThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@Nonnull Runnable r) {
        print(this + " creating new Thread");
        Thread t = new Thread(r);
        print("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        print("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}
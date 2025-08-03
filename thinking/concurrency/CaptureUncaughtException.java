package thinking.concurrency;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * 线程池中捕获异常; 实现 {@link Thread.UncaughtExceptionHandler}
 *
 * @author zqw
 * @date 2021/1/18
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = ThreadObjectPool.newFixedThreadPool(1);
        ThreadObjectPool.buildThreadFactory(exec, new HandleThreadFactory());
        exec.execute(new ExceptionThreadCaught());
        exec.shutdown();
    }

}

class ExceptionThreadCaught implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        Print.println("run() by " + t);
        Print.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }

}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Print.println("caught " + e);
    }
}

class HandleThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@Nonnull Runnable r) {
        Print.println(this + " creating new Thread");
        Thread t = new Thread(r);
        Print.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        Print.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

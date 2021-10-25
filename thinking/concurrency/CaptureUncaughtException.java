package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static util.Print.print;

/**
 * @author:qiming 
 * @date: 2021/1/18
 */
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandleThreadFactory());
        exec.execute(new ExceptionThread());
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
    public Thread newThread(Runnable r) {
        print(this + " creating new Thread");
        Thread t = new Thread(r);
        print("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        print("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}
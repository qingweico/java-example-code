package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

/**
 * Catch exceptions
 *
 * @author:qiming
 * @date: 2021/1/18
 * {ThrowsException}
 */
public class ExceptionThread implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
        exec.shutdown();
    }
}

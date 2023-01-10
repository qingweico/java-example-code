package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;

/**
 * Catch exceptions
 *
 * @author zqw
 * @date 2021/1/18
 */
class ExceptionThread implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        ExecutorService pool = ThreadPoolBuilder.builder().build();
        pool.execute(new ExceptionThread());
        pool.shutdown();
    }
}

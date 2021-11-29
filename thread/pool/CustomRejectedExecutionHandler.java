package thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author:qiming
 * @date: 2021/9/29
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("Custom RejectedExecutionHandler!");
        r.run();
    }

    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool();
        ExecutorService executorService = pool.newFixedThreadPool(3, 4, 5);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ": " + finalI));
        }
        executorService.shutdown();

    }


}

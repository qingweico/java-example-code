package thread.pool;

import util.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zqw
 * @date 2021/9/29
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println("task rejected!");
    }

    public static void main(String[] args) {
        ExecutorService executorService = CustomThreadPool.newFixedThreadPool(2, 2, 5);
        for (int i = 0; i < Constants.TEN; i++) {
            int finalI = i;
            executorService.execute(() -> System.out.println(Thread.currentThread().getName() + ": " + finalI));
        }
        executorService.shutdown();

    }
}

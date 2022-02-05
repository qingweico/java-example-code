package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable
 *
 * @author zqw
 * @date 2020/12/17
 */
public class CallableTask implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 5, 2);
        CallableTask callableTask = new CallableTask();
        FutureTask<String> futureTask = new FutureTask<>(callableTask);
        pool.submit(futureTask);
        String returnValue = null;
        try {
            // block
            returnValue = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(returnValue);

        List<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < Constants.FIVE; i++) {
            Callable<String> callable = new CallableTask();
            Future<String> f = pool.submit(callable);
            list.add(f);
        }
        pool.shutdown();
        for (Future<String> f : list) {
            System.out.println(f.get());
        }
    }

    @Override
    public String call() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Callable Task";
    }
}

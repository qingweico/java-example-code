package thread.concurrency.task;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.io.Print;
import cn.qingweico.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable {@link Callable}
 *
 * @author zqw
 * @date 2020/12/17
 */
class CallableTask implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService pool = ThreadObjectPool.newFixedThreadPool(5);
        CallableTask callableTask = new CallableTask();
        FutureTask<String> futureTask = new FutureTask<>(callableTask);
        pool.submit(futureTask);
        String returnValue = null;
        try {
            // block
            returnValue = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            Print.err(e.getMessage());
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
            Print.err(e.getMessage());
        }
        return "Callable Task";
    }
}

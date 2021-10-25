package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2020/12/17
 */
public class CallableUsage implements Callable<String> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableUsage callableUsage = new CallableUsage();
        FutureTask<String> futureTask = new FutureTask<>(callableUsage);
        Thread t = new Thread(futureTask);
        t.start();
        String returnValue = null;
        try {
            // block
            returnValue = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(returnValue);

        print("----------");


        List<Future<String>> list = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Callable<String> callable = new CallableUsage();
            Future<String> f = service.submit(callable);
            list.add(f);
        }
        service.shutdown();
        for (Future<String> f : list) {
            System.out.println(f.get());
        }
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "Hello Callable";
    }
}

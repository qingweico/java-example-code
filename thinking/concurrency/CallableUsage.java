package thinking.concurrency;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author:qiming
 * @date: 2021/1/15
 */
// Runnable is a stand-alone task that performs work, but it does not return any value.

public class CallableUsage {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            // The submit method returns the Future object, which is parameterized with the
            // specific type of Callable return result.

            results.add(exec.submit(new TaskWithTResult(i)));
        }
        for (Future<String> fs : results) {
            try {
                System.out.println(fs.isDone());
                // CancellationException
                fs.cancel(true);
                System.out.println(fs.get(1, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            } finally {
                exec.shutdown();
            }

        }
    }
}
// Its type parameter represents the value returned from the method call.
class TaskWithTResult implements Callable<String> {
    private final int id;

    public TaskWithTResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return "result of TaskWithResult " + id;
    }
}

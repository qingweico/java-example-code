package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author zqw
 * @date 2021/1/15
 */
// Runnable is a stand-alone task that performs work, but it does not return any value.

class CallableUsage {
    public static void main(String[] args) {
        int threadCount = 10;
        ExecutorService exec = ThreadPoolBuilder.builder().build();
        ArrayList<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {

            // The submit method returns the Future object, which is parameterized with the
            // specific type of Callable return result.

            results.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs : results) {
            try {
                System.out.println(fs.isDone());
                // CancellationException
                fs.cancel(true);
                System.out.println(fs.get(1, TimeUnit.SECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                Print.err(e.getMessage());
            } finally {
                exec.shutdown();
            }

        }
    }
}
// Its type parameter represents the value returned from the method call.

class TaskWithResult implements Callable<String> {
    private final int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return "result of TaskWithResult " + id;
    }
}

package thread.concurrency.task;

import com.google.common.util.concurrent.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import thread.pool.ThreadPoolBuilder;
import util.Print;
import util.RandomDataUtil;

import java.util.concurrent.ExecutionException;

/**
 * Guava future
 *
 * @author zqw
 * @date 2024/5/23
 */
public class GuavaFutureTest {
    ListeningExecutorService service = MoreExecutors.listeningDecorator(ThreadPoolBuilder.builder().build());

    @Test
    public void callback() throws ExecutionException, InterruptedException {
        ListenableFuture<String> future = service.submit(() -> RandomDataUtil.address());
        // 将异步任务的输出转换成另一种格式或类型 Futures.transform
        Futures.addCallback(future, new FutureCallback<>() {
            @Override
            public void onSuccess(String result) {
                Print.grace("onSuccess", result);
            }

            @Override
            public void onFailure(@NotNull Throwable t) {
                Throwable cause = t.getCause();
                String message = (cause != null) ? cause.getMessage() : t.getMessage();
                Print.grace("onFailure", message);
            }
        }, service);
        future.get();
        service.shutdown();
    }
}

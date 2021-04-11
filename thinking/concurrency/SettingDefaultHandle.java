package thinking.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author:qiming
 * @date: 2021/4/9
 */
public class SettingDefaultHandle {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(
                new MyUncaughtExceptionHandler()
        );
        ExecutorService exc = Executors.newCachedThreadPool();
        exc.execute(new ExceptionThread0());

        // caught java.lang.RuntimeException

    }
}

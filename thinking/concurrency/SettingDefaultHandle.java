package thinking.concurrency;

import thread.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 设置线程异常处理器
 *
 * @author zqw
 * @date 2021/4/9
 */
class SettingDefaultHandle {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(
                new MyUncaughtExceptionHandler()
        );
        ExecutorService pool = ThreadPoolBuilder.custom().builder();
        pool.execute(new ExceptionThread());
        pool.shutdown();

        // caught java.lang.RuntimeException

    }
}

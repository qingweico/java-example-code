package thread.pool;

import org.jetbrains.annotations.NotNull;
import util.constants.Symbol;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zqw
 * @date 2023/12/9
 */
public class NamedThreadFactory implements ThreadFactory {
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public NamedThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r,
                namePrefix + Symbol.WHIFFLETREE + threadNumber.getAndIncrement());
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        t.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
        return t;
    }
}

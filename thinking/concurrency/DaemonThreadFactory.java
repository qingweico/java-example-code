package thinking.concurrency;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadFactory;

/**
 * @author zqw
 * @date 2021/4/8
 */
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@Nullable Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}

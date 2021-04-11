package thinking.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * @author:qiming
 * @date: 2021/4/8
 */
public class DaemonThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}

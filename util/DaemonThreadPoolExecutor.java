package util;

import thinking.concurrency.DaemonThreadFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/4/8
 */
public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
    public DaemonThreadPoolExecutor() {
        super(0,
                Integer.MAX_VALUE,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new DaemonThreadFactory());

    }
}

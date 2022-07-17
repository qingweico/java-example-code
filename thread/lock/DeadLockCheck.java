package thread.lock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用 {@link java.lang.management.ThreadMXBean} 检测死锁
 *
 * @author zqw
 * @date 2022/7/16
 */
public class DeadLockCheck {
    public static void main(String[] args) {
        ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
        Runnable r = () -> {
            long[] threadIds = mbean.findDeadlockedThreads();
            if (threadIds != null) {
                ThreadInfo[] threadInfos = mbean.getThreadInfo(threadIds);
                System.out.println("dead lock thread: ");
                for (ThreadInfo ti : threadInfos) {
                    System.out.println(ti.getThreadName());
                }
            }else {
                System.out.println("not find dead lock threads");
            }
        };
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);

        // delay 5 seconds, per 10 second do deadlock scan
        scheduled.scheduleAtFixedRate(r, 5, 10, TimeUnit.SECONDS);
    }

}

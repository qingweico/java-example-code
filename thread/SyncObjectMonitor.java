package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * synchronized {@code see openjdk}
 * os_linux.cpp >>> os::PlatformEvent::park() >>> pthread_mutex_lock >>> pthread_mutex_unlock
 * <p>
 * ObjectMonitor: JVM C++ an object include: WaitSet, EntryList, OwnerThread, recursions
 *
 * @author zqw
 * @date 2021/6/27
 */
class SyncObjectMonitor {
    static List<Runnable> list = new ArrayList<>();
    static final Object O = new Object();
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 10, 10);

    public static void main(String[] args) {
        for (int i = 0; i < Constants.TEN; i++) {
            list.add(() -> {
                // Don't get lock and enter EntryList
                // The EntryList of synchronized follows the FILO.
                System.out.println("\t" + Thread.currentThread().getName());
                synchronized (O) {
                    System.out.println("\t" + Thread.currentThread().getName() + "\t");
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        synchronized (O) {
            System.out.println("Start Sequence:");
            for (Runnable runnable : list) {
                pool.execute(runnable);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Awake Sequence:");
        pool.shutdown();
    }
}

package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * synchronized see openjdk
 * os_linux.cpp>>>os::PlatformEvent::park()>>> pthread_mutex_lock>>>pthread_mutex_unlock
 * <p>
 * ObjectMonitor: JVM C++ a object include: WaitSet, EntryList, OwnerThread, recursions
 *
 * @author:qiming
 * @date: 2021/6/27
 */
public class SyncObjectMonitor {

    static List<Thread> list = new ArrayList<>();

    static final Object lock = new Object();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(() -> {

                synchronized (lock) {
                    // Don't get lock and Enter EntryList
                    // The EntryList of synchronized follows the FILO
                    System.out.println("\t" + Thread.currentThread().getName() + " -----> thread execute...\t");

                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "t" + i);
            list.add(thread);
        }


        synchronized (lock) {
            System.out.println("Start sequence:");
            for (Thread thread : list) {
                System.out.println("\t" + thread.getName() + "---->");
                thread.start();

                try {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Awake sequence:");
    }


}

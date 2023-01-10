package thread.lock;

import thread.pool.ThreadPoolBuilder;
import util.constants.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * synchronized is a mutex lock and is also a spin lock.
 * {@code openjdk}
 * Java ordinary synchronized method: ACC_SYNCHRONIZE
 * Java static synchronized method: ACC_SYNCHRONIZE; ACC_STATIC
 * Synchronized code block: Interpret monitorenter and monitorexit in Java bytecode
 * {@code bytecodeInterpreter.cpp; CASE(_monitorenter); CASE(_monitorexit)}
 * spin lock:
 * {@code interpreterRuntime.cpp#InterpreterRuntime::monitorenter; InterpreterRuntime::monitorexit}
 * {@code synchronizer.cpp#ObjectSynchronizer::fast_enter; ObjectSynchronizer::slow_enter}
 * {@code objectMonitor.cpp#ObjectMonitor::enter(TRAPS); ObjectMonitor::EnterI (TRAPS)#TrySpin}
 * mutex_lock:
 * {@code os_linux.cpp#os::PlatformEvent::park()#pthread_mutex_lock();
 * os::PlatformEvent::unpark()#pthread_mutex_unlock;}
 * {@see mutex_lock.c}
 * {@see spin_lock.c}
 * <p>
 * {@code park.hpp}
 * ParkEvent are used for Java-level "monitor" synchronization.
 * Parkers are used by JSR166-JUC park-unpark.
 * ObjectMonitor: JVM C++ an object include: WaitSet, EntryList, OwnerThread, recursions etc.
 * monitor: 管程; 监视器 在Hotspot中由ObjectMonitor实现 {@code ObjectMonitor.cpp} 每个对象都具有一个
 * ObjectMonitor对象.
 *
 * @author zqw
 * @date 2021/6/27
 */
class SyncObjectMonitor {
    static List<Runnable> list = new ArrayList<>();
    static final Object O = new Object();
    static ExecutorService pool = ThreadPoolBuilder.builder()
            .preStartAllCore(true)
            .isEnableMonitor(true)
            .build();

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

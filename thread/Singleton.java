package thread;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

/**
 * DCL instruction reordering
 *
 * @author zqw
 * @date 2021/11/24
 */
@SuppressWarnings("all")
class Singleton {
    private static Singleton singleton = null;
    private Integer x = 1;

    public static Singleton getInstance() {
        if (singleton == null) {
            synchronized (Singleton.class) {
                if (singleton == null) {
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

    public static void removeInstance() {
        singleton = null;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService exec = Executors.newCachedThreadPool();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(500);
        for (int i = 0; i < 1000_000; i++) {
            cyclicBarrier.reset();
            List<Callable<Singleton>> list = new ArrayList<>();
            for (int j = 0; j < 500; j++) {
                list.add(() -> {
                    cyclicBarrier.await();
                    Singleton singleton = getInstance();
                    if (singleton.x == null) {
                        throw new RuntimeException("object not initialized!");
                    }
                    return singleton;
                });
            }
            List<Future<Singleton>> futures = exec.invokeAll(list);
            Set<Singleton> singletonSet = new HashSet<>();
            for (Future<Singleton> future : futures) {
                singletonSet.add(future.get());
            }
            if (singletonSet.size() > 1) {
                System.err.print("Object instances are not unique!");
                System.exit(-1);
            }
            Singleton.removeInstance();
        }
        exec.shutdown();
        System.exit(0);
    }
}

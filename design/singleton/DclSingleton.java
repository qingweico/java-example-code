package design.singleton;

import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 懒汉式DCL
 * lazy loading
 *
 * @author zqw
 * @date 2021/2/3
 * @see org.apache.commons.lang3.concurrent.LazyInitializer
 */
public class DclSingleton {
    private static long id = 0;

    @Override
    public String toString() {
        return "[ Singleton id = " + id + " ]";
    }

    private DclSingleton() {
        id++;
    }

    /**
     * Ensure that instructions around student are not reordered.
     */
    private static volatile DclSingleton instance = null;

    /**
     * DCL(Double Check Lock)
     *
     * @return the instance of DclSingleton
     */
    public static DclSingleton getInstance() {
        if (instance == null) {
            synchronized (DclSingleton.class) {
                if (instance == null) {
                    instance = new DclSingleton();
                }
            }
        }
        return instance;
    }

    /**
     * JDK9
     * Allowing local instructions of a ref to be reordered improves performance.
     */
    private static final AtomicReference<DclSingleton> REF = new AtomicReference<>();

    public static DclSingleton getRef() {
        if (REF.getAcquire() == null) {
            synchronized (DclSingleton.class) {
                if (REF.getAcquire() == null) {
                    REF.setRelease(instance);
                }
            }
        }
        return REF.getAcquire();
    }

    public static void main(String[] args) {
        ThreadObjectPool pool = new ThreadObjectPool();
        for (int i = 0; i < Constants.TWENTY; i++) {
            pool.execute(() -> {
                System.out.println(DclSingleton.getInstance());
                System.out.println(DclSingleton.getRef().hashCode());
            });
        }
        pool.shutdown();
    }
}

package design.creational.singleton;

import cn.qingweico.concurrent.pool.ThreadObjectPool;
import cn.qingweico.constants.Constants;

import java.util.concurrent.atomic.AtomicReference;
import java.util.random.RandomGeneratorFactory;

/**
 * 懒汉式DCL
 * lazy loading
 *
 * @author zqw
 * @date 2021/2/3
 * @see org.apache.commons.lang3.concurrent.LazyInitializer
 * @see JrtFileSystemProvider#getTheFileSystem
 * @see java.util.ResourceBundle#handleKeySet
 * @see RandomGeneratorFactory#getConstructors(Class)
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
     * The singleton instance using double-checked locking pattern.
     * Marked as volatile to prevent instruction reordering and ensure
     * thread-safe publication of the fully initialized instance.
     */
    private static volatile DclSingleton instance = null;

    /**
     * DCL (Double Check Lock)
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

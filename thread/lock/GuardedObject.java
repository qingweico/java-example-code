package thread.lock;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * Guarded Suspension
 *
 * @author zqw
 * @date 2022/2/7
 */
public class GuardedObject<T> {
    T obj;
    final Lock lock = new ReentrantLock();
    final Condition done = lock.newCondition();
    final int timeout = 1;
    final Map<Object, GuardedObject<T>> gos = new ConcurrentHashMap<>();

    <R> GuardedObject<T> create(R key) {
        GuardedObject<T> go = new GuardedObject<>();
        gos.put(key, go);
        return go;
    }


    T get(Predicate<T> p) {
        lock.lock();
        try {
            // MESA 管程推荐写法
            while (!p.test(obj)) {
                done.await(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return obj;
    }

    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
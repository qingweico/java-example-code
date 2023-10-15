package thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 使用读写锁 + Map构建本地缓存
 *
 * @author zqw
 * @date 2022/2/4
 */
public class LocalCache<K, V> {
    final Map<K, V> cache = new HashMap<>();
    final ReadWriteLock rw = new ReentrantReadWriteLock();
    final Lock r = rw.readLock();
    final Lock w = rw.writeLock();

    V get(K k) {
        V v;
        r.lock();
        try {
            v = cache.get(k);
        } finally {
            r.unlock();
        }
        if (v != null) {
            return v;
        }
        w.lock();
        try {
            v = cache.get(k);
            if (v == null) {
                v = read();
                cache.put(k, v);
            }

        } finally {
            w.unlock();
        }
        return v;
    }

    V put(K key, V value) {
        w.lock();
        try {
            return cache.put(key, value);
        } finally {
            w.unlock();

        }
    }

    V read() {
        return null;
    }
}

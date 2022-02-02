package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2020/12/18
 */
public class ConcurrentMapAndSet {
    public static void main(String[] args) {
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 10, 5);
        // Use ConcurrentHashMap instead of HashMap can avoid throw ConcurrentModificationException.
        Map<String, String> map = new ConcurrentHashMap<>(2);

        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < Constants.TEN; i++) {
            int finalI = i;
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                set.add("a");
                System.out.println(set);
                map.put(UUID.randomUUID().toString().substring(0, finalI + 1), String.valueOf(finalI));
                System.out.println(map);
            });
        }
        pool.shutdown();

    }
}

package thread;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2020/12/18
 */
public class ConcurrentMapAndSet {
    public static void main(String[] args) {
        // Use ConcurrentHashMap instead of HashMap can
        // avoid throw ConcurrentModificationException
        Map<String, String> map = new ConcurrentHashMap<>(2);

        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                set.add("a");
                System.out.println(set);
                map.put(UUID.randomUUID().toString().substring(0, finalI + 1), String.valueOf(finalI));
                System.out.println(map);
            }, "t").start();
        }

    }
}

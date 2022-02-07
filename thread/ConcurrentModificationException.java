package thread;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2020/12/18
 */
class ConcurrentModificationException {
    public static void main(String[] args) {
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(5, 10, 5);
        // Using ArrayList will throw ConcurrentModificationException, because
        // ArrayList is thread-unsafe, so we could use Vector instead of ArrayList,
        // or using CopyOnWriteArrayList, or using the Collections.synchronizedList(Synchronous container class)
        // also is a good choice.
        List<String> list = new ArrayList<>();

        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add("a");
                System.out.println(list);
            });
        }
        pool.shutdown();
    }
}

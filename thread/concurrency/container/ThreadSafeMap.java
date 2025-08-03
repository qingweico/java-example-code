package thread.concurrency.container;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * {@link ConcurrentMap} 只能保证提供的读写性操作是原子性的, ConcurrentMap 的整体性能要优于 Hashtable
 * 但是并不能代替Hashtable,ConcurrentMap是弱一致性的
 * {@link ConcurrentMap#get(Object)}  {@link ConcurrentMap#size()} 等方法没有用到锁,
 * 因此有可能会导致某次读无法马上获取写入的数据
 * 由于 {@link java.util.TreeMap} 要实现高效的线程安全是非常困难的,所以没有 {@code ConcurrentTreeMap}
 * {@link Hashtable}
 * {@link ConcurrentSkipListMap}
 * {@link ConcurrentHashMap}
 * {@link HashMap}
 * @author zqw
 * @date 2022/7/4
 */
@Slf4j
class ThreadSafeMap {
    /**线程个数**/
    private static final int THREAD_COUNT = 10;
    /**总元素数量**/
    private static final int ITEM_COUNT = 1000;

    /**
     * 用来获得一个指定元素数量模拟数据的ConcurrentHashMap
     *
     * @param count 元素个数
     * @return {@code ConcurrentHashMap}
     */
    private static ConcurrentHashMap<String, Long> getData(int count) {
        return LongStream.rangeClosed(1, count).boxed().collect(Collectors
                .toConcurrentMap(i -> UUID.randomUUID().toString(),
                Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
    }

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<String, Long> concurrentHashMap = getData(ITEM_COUNT - 100);
        // 初始900个元素
        log.info("init size:{}", concurrentHashMap.size());

        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
            synchronized (concurrentHashMap) {
                // 查询还需要补充多少个元素
                int gap = ITEM_COUNT - concurrentHashMap.size();
                log.info("need size:{}", gap);
                // 补充元素
                concurrentHashMap.putAll(getData(gap));
            }
        }));
        forkJoinPool.shutdown();
        if (!forkJoinPool.awaitTermination(1, TimeUnit.HOURS)) {
            forkJoinPool.shutdownNow();
        }
        log.info("finish size:{}", concurrentHashMap.size());
    }
}

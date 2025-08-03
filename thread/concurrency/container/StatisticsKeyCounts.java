package thread.concurrency.container;

import org.apache.commons.lang3.time.StopWatch;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import cn.qingweico.io.Print;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zqw
 * @date 2022/7/4
 */
class StatisticsKeyCounts {
    private static final int LOOP_COUNT = 10000000;
    private static final int THREAD_COUNT = 10;
    private static final int ITEM_COUNT = 10;
    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start();
        StatisticsKeyCounts inst = new StatisticsKeyCounts();
        Map<String, Long> map = inst.statisticsKeyCounts();
        Print.printMap(map);
        sw.stop();
        System.out.println(sw.getTime(TimeUnit.MILLISECONDS));


    }

    public Map<String, Long> statisticsKeyCounts() throws InterruptedException {
        ConcurrentHashMap<String, LongAdder> freq = new ConcurrentHashMap<>(ITEM_COUNT);
        ForkJoinPool fjp = new ForkJoinPool(THREAD_COUNT);
        fjp.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(i -> {
            String key = "Key" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
            freq.computeIfAbsent(key, k -> new LongAdder()).increment();
        }));
        fjp.shutdown();
        if(!fjp.awaitTermination(1, TimeUnit.HOURS)) {
            fjp.shutdownNow();
        }
        // LongAdder -> long
        return freq.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                v -> v.getValue().longValue()));
    }
}

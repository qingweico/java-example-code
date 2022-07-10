package util;

import thread.pool.CustomThreadPool;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 0: 符号位
 * 1 ~ 41: 时间戳
 * 42 ~ 51: 机器id
 * 52 ~ 63: 序列号
 * @author zqw
 * @date 2022/2/25
 */
public class SnowflakeIdWorker {
    private static long workId;
    private static long sequence = 0L;
    private static final long SERVICE_ID = Math.abs(System.getenv().hashCode()) % 32;
    private static long lastTime = System.currentTimeMillis();
    private static final long MAX_SEQUENCE = (1 << 12) - 1;
    private static final int THREAD_COUNT = Constants.NUM_300;

    static {
        try {
            workId = Math.abs(Inet4Address.getLocalHost().getHostAddress().hashCode()) % 32;
        } catch (UnknownHostException e) {
            workId = new Random().nextLong() % 32;
        }
    }

    private SnowflakeIdWorker() {
    }

    public synchronized static Long nextId() {
        long l = System.currentTimeMillis();
        if (lastTime == l) {
            ++sequence;
        } else {
            lastTime = l;
            sequence = 0;
        }
        if (sequence > MAX_SEQUENCE) {
            nextId();
        }
        return lastTime << 22 | SERVICE_ID << 17 | workId << 12 | sequence;
    }

    public static void main(String[] args) throws InterruptedException {
        Map<Long, Long> map = new ConcurrentHashMap<>(1000);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.TEN_THOUSAND; j++) {
                    Long aLong = SnowflakeIdWorker.nextId();
                    map.put(aLong, aLong);
                }
                latch.countDown();
            });
        }
        latch.await();
        pool.shutdown();
        // 300_0000
        System.out.println(map.size());
    }
}

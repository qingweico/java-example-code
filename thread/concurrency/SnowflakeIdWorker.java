package thread.concurrency;

import thread.pool.CustomThreadPool;
import util.Constants;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
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

    public SnowflakeIdWorker() {
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
        final ExecutorService pool = CustomThreadPool.newFixedThreadPool(300);
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.TEN_THOUSAND; j++) {
                    Long aLong = SnowflakeIdWorker.nextId();
                    map.put(aLong, aLong);
                }
            });
        }
        Thread.sleep(1000);
        pool.shutdown();
        System.out.println(map.size());
    }
}

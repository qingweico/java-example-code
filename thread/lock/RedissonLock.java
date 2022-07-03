package thread.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;

/**
 * 基于 Redisson 的 Redis 分布式锁
 * {@code 理解Paxos;Raft一致性算法}
 * @author zqw
 * @date 2022/3/13
 */
class RedissonLock {
    static int inventory = 5;
    static int THREAD_COUNT = Constants.TEN;
    private static final String REDIS_SERVER = "redis://119.29.35.129:6379";
    private static final String REDIS_PASSWORD = "990712";
    private static final String REDIS_LOCK_KEY = "redis_lock";
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_SERVER).setPassword(REDIS_PASSWORD);
        final RedissonClient client = Redisson.create(config);
        final RLock lock = client.getLock(REDIS_LOCK_KEY);
        for (int i = 0; i < THREAD_COUNT; i++) {
            pool.execute(() -> {
                lock.lock();
                if (inventory > 0) {
                    inventory--;
                    System.out.println(inventory);
                }
                lock.unlock();
            });
        }
        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                client.shutdown();
                break;
            }

        }
    }
}

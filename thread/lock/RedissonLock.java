package thread.lock;

import frame.redis.DistributedLock;
import frame.redis.RedissonConfig;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;

/**
 * 基于 Redisson 的 Redis 分布式锁
 * {@code 理解Paxos;Redis 提供了Raft(Paxos 简化版本)一致性算法}
 * 基于 redis setnx 和 lua 实现 {@link DistributedLock}
 * Redisson 分布式锁原理
 * 可重入: 利用 hash 结构记录线程 id 和重入次数
 * 可重试: 利用信号量和发布订阅功能实现等待、唤醒、获取锁失败的重试机制
 * 超时续约: 利用 watch dog 隔一段时间, 重置超时时间
 *
 * @author zqw
 * @date 2022/3/13
 */
public class RedissonLock {
    static int inventory = 100;
    static int THREAD_COUNT = Constants.NUM_100;
    private static final String REDIS_LOCK_KEY = "redis_lock";
    static ExecutorService pool = ThreadObjectPool.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) {
        RedissonConfig config = new RedissonConfig();
        final RedissonClient client = config.redisson();
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

package frame.redis;

import redis.clients.jedis.Jedis;
import thread.pool.CustomThreadPool;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * --------------- 基于 Redis 实现分布式锁 ---------------
 *
 * @author zqw
 * @date 2022/7/13
 * 基于 {@code Redisson} 实现分布式锁 {@link thread.lock.RedissonLock}
 */
public class DistributedLock {
    private static final String KEY = "lock_key";
    private static int inventory = 100;
    private final static ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 2, 100);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            pool.execute(() -> {

                // TODO
                Jedis jedis = RedisClient.getJedis();
                String threadId = String.valueOf(Thread.currentThread().getId());
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] " + "then return redis.call('del', KEYS[1]) else return 0 end";
                Object result = jedis.eval(script, Collections.singletonList(KEY), Collections.singletonList(threadId));
                // 获取锁成功
                if (result.equals(1)) {

                    inventory--;
                }
                try {
                    // 获取锁成功
                    if (jedis.setnx(KEY, threadId) == 1) {
                        jedis.expire(KEY, 10);
                        if (inventory > 0) {
                            inventory--;
                            System.out.println(inventory);
                        }
                    }
                } finally {
                    // 判断是否持有锁,避免释放别人的锁
                    if (Objects.equals(jedis.get(KEY), threadId)) {
                        jedis.del(KEY);
                    }
                }
            });
        }
        pool.shutdown();
    }
}

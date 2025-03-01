package frame.redis;

import redis.clients.jedis.Jedis;
import thread.pool.ThreadPoolBuilder;
import util.Print;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * --------------- 基于 Redis 实现分布式锁 ---------------
 *
 * @author zqw
 * @date 2022/7/13
 * 基于 {@code Redisson} 实现分布式锁 {@link thread.lock.RedissonLock}
 */
public class DistributedLock {

    private static final String LOCK_SUCCESS = "OK";
    /**NX: key not exist. then set  XX: key exist. then set*/
    private static final String SET_IF_NOT_EXIST = "NX";
    /**EX: expire unit(s)  PX: expire unit(ms)*/
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final int DEFAULT_EXPIRE_TIME = 1000;
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_KEY = "lock_key";
    private static int inventory = 100;


    /**
     * 获取UUID
     *
     * @return UUID
     */
    public static String getLockUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 等待获取锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 超时时间内是否获取锁
     */
    public static boolean waitGetDistributedLock(String lockKey, String requestId) {
        return waitGetDistributedLock(lockKey, requestId, DEFAULT_EXPIRE_TIME);
    }


    public static boolean waitGetDistributedLock(String lockKey, String requestId, int expireTime) {
        try (Jedis redis = RedisClient.getJedis()) {
            while (true) {
                if (tryGetDistributedLock(redis, lockKey, requestId, expireTime)) {
                    return true;
                }
            }
        }
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    private static boolean tryGetDistributedLock(Jedis redis, String lockKey, String requestId, int expireTime) {
        String result = redis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);

    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(String lockKey, String requestId) {
        try (Jedis redis = RedisClient.getJedis()) {
            // lua 脚本
            // key 类型参数放在 KEYS 数组中,其他参数会放在 ARGV 数组中; lua中数组的下标是从1开始的
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = redis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return RELEASE_SUCCESS.equals(result);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int client = 100;
        ExecutorService pool = ThreadPoolBuilder.builder().corePoolSize(100).maxPoolSize(100).preStartAllCore(true).build();
        for (int i = 0; i < client; i++) {
            pool.execute(() -> {
                final long id = Thread.currentThread().getId();
                String requestId = getLockUuid() + id;
                try {
                    if (waitGetDistributedLock(LOCK_KEY, requestId)) {
                        // 获取锁成功
                        if (inventory > 0) {
                            inventory--;
                            Print.grace("requestId", requestId);
                            Print.grace("inventory", inventory);
                        }
                    }
                } finally {
                    // 释放锁
                    if (!releaseDistributedLock(LOCK_KEY, requestId)) {
                        Print.printf("release lock fail!, {}", requestId);
                    }
                }
            });
        }
        pool.shutdown();
    }
}

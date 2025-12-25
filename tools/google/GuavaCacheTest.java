package tools.google;


import cn.qingweico.concurrent.ThreadPoolTask;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GuavaCache 是一套非常完善的本地缓存机制(JVM缓存) {@link com.google.common.cache.LoadingCache}
 *
 * @author zqw
 * @date 2023/8/15
 * @see com.github.benmanes.caffeine.cache.LoadingCache
 */
public class GuavaCacheTest {
    private static final Cache<String, Integer> MEMORY_CACHE =
            CacheBuilder.newBuilder().build();

    private static final Cache<String, AtomicInteger> ATOMIC_MEMORY_CACHE =
            CacheBuilder.newBuilder().build();

    // 一种线程安全的计数器累加方式
    private static void countAdder(IntAdder intAdder) {
        if (intAdder == null) {
            return;
        }

        String cacheKey = intAdder.getKey();

        MEMORY_CACHE.asMap().compute(cacheKey, (k, v) -> {
            if (v == null) {
                return 1;
            }
            return v + 1;
        });
    }

    // 使用 AtomicInteger
    private void countByAtomic(IntAdder intAdder) {
        if (intAdder == null) {
            return;
        }

        String cacheKey = intAdder.getKey();

        AtomicInteger counter = ATOMIC_MEMORY_CACHE.getIfPresent(cacheKey);
        if (counter == null) {
            AtomicInteger newCounter = new AtomicInteger(1);
            // putIfAbsent 返回的是旧值, 此时如果有两个线程都到达此处, 一个put成功则返回 null(原始值已被设置为newCounter)
            // 一个put失败返回1, 执行下面的自增操作,所以每个线程(不管成功还是失败)都贡献了一次 +1,只是方式不同
            AtomicInteger olderValue = ATOMIC_MEMORY_CACHE.asMap().putIfAbsent(cacheKey, newCounter);
            if (olderValue != null) {
                olderValue.incrementAndGet();
            }
        } else {
            counter.incrementAndGet();
        }
    }


    @Test
    public void countAdder() {
        ThreadPoolTask.waitForExec(1000, () -> {
            IntAdder intAdder = new IntAdder();
            intAdder.setKey(RandomStringUtils.randomAlphanumeric(6));
            countAdder(intAdder);
        });

        int sum = MEMORY_CACHE.asMap()
                .values()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(sum);
    }


    @Test
    public void countByAtomic() {
        ThreadPoolTask.waitForExec(1000, () -> {
            IntAdder intAdder = new IntAdder();
            intAdder.setKey(RandomStringUtils.randomAlphanumeric(6));
            countByAtomic(intAdder);
        });

        int sum = ATOMIC_MEMORY_CACHE.asMap()
                .values()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(AtomicInteger::get)
                .sum();

        System.out.println(sum);
    }

    // 比较经典的错误使用方式 共有三个严重的问题
    private void mistake(IntAdder intAdder) {
        if (intAdder == null) {
            return;
        }
        // 1 getIfPresent 和 put 操作不是原子操作,最后的 else put操作没有完全在临界区内, 从而造成更新覆盖丢失
        // 读缓存没有加锁, 写缓存时部分有锁, cache内部有自己的锁
        // 同一把锁, 没有包住所有相关读写, JMM里叫做锁语义割裂
        String cacheKey = intAdder.getKey();
        Integer value = MEMORY_CACHE.getIfPresent(cacheKey);
        if (value == null) {
            // 2 使用字符串的intern形式作为monitor
            // intern() 会进入 JVM 字符串常量池, 会变成全局共享的, 即有可能被其他的代码使用同一个字符串锁
            // 如果有大量不同的key, 会造成常量池膨胀从而带来内存压力
            synchronized (cacheKey.intern()) {
                // 3 DCL 在这里没有意义, 只有状态完全受同一把锁保护时才有意义
                value = MEMORY_CACHE.getIfPresent(cacheKey);
                if (value == null) {
                    MEMORY_CACHE.put(cacheKey, 1);
                } else {
                    MEMORY_CACHE.put(cacheKey, value + 1);
                }
            }
        } else {
            MEMORY_CACHE.put(cacheKey, value + 1);
        }
    }


    @Test
    public void mistake() {
        String sharedKey = RandomStringUtils.randomAlphanumeric(6);
        ThreadPoolTask.waitForAllOf(1000, () -> {
            IntAdder intAdder = new IntAdder();
            intAdder.setKey(sharedKey);
            mistake(intAdder);
        });

        int sum = MEMORY_CACHE.asMap()
                .values()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(sum);
    }


    @Data
    static class IntAdder {
        private String key;
        private int value;
    }
}

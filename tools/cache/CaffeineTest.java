package tools.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Test;
import thread.cas.UnsafeSupport;
import util.Print;
import util.process.ProcessExecutor;

import java.time.Duration;

/**
 * Caffeine 基于 Java8 实现的 JVM 本地高性能缓存, see [Google Guava]
 * <a href="https://github.com/ben-manes/caffeine"></a>
 *
 * @author zqw
 * @date 2022/9/15
 */
public class CaffeineTest {

    @Test
    public void base() {
        Cache<String, String> cache = Caffeine.newBuilder().build();
        cache.put("default", "default");
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");

        System.out.println(cache.getIfPresent("k1"));
        String def = cache.get("default", k -> {
            // 如果 default 对用的值不存在, 则根据 k 去查询 db
            return "k";
        });
        System.out.println(def);
    }

    @Test
    public void evictPolicy() {
        // 基于容量
        Cache<String, String> cache = Caffeine.newBuilder().
                maximumSize(1).
                build();
        cache.put("k1", "v1");
        cache.put("k2", "v2");
        cache.put("k3", "v3");
        UnsafeSupport.shortWait(10);
        Print.print(cache.getIfPresent("k1"));
        Print.print(cache.getIfPresent("k2"));
        Print.print(cache.getIfPresent("k3"));
        // 基于时间
        cache = Caffeine.newBuilder().
                // 一秒钟后过期
                expireAfterWrite(Duration.ofSeconds(1)).
                build();
        cache.put("k4", "v4");
        ProcessExecutor.waitFor(1);
        Print.print(cache.getIfPresent("k4"));
        // 基于引用 即设置缓存为软引用或者为弱引用; 利用 GC 来清除缓存数据,性能较差,不推荐

        // tips: 当一个元素过期后,并不会立即清除,而是在一次读
        // 或写后或者在空闲时间完成对失效数据的清除
    }
}

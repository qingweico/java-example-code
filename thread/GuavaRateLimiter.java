package thread;

import com.google.common.util.concurrent.RateLimiter;
import thread.pool.ThreadObjectPool;
import util.constants.Constants;

import java.util.concurrent.ExecutorService;

/**
 * Guava 限流器[令牌桶算法]
 * 漏桶算法
 *
 * @author zqw
 * @date 2022/2/7
 */
@SuppressWarnings("all")
class GuavaRateLimiter {
    public static void main(String[] args) {
        // 限流器: 2个请求/秒
        RateLimiter limiter = RateLimiter.create(2.0);
        var ref = new Object() {
            long prev = System.nanoTime();
        };
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(10);
        for (int i = 0; i < Constants.NUM_100; i++) {
            limiter.acquire();
            pool.execute(() -> {
                long cur = System.nanoTime();
                // 基本都接近500ms
                System.out.println((cur - ref.prev) / 1_000_000 + "ms");
                ref.prev = cur;
            });
        }
        pool.shutdown();
    }
}

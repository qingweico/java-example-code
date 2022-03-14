package thread;

import com.google.common.util.concurrent.RateLimiter;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.ExecutorService;

/**
 * @author zqw
 * @date 2022/2/7
 */
@SuppressWarnings("all")
public class GuavaRateLimiter {

    public static void main(String[] args) {
        // 限流器: 2个请求/秒
        RateLimiter limiter = RateLimiter.create(2.0);
        var ref = new Object() {
            long prev = System.nanoTime();
        };
        ExecutorService pool = CustomThreadPool.newFixedThreadPool(10);
        for (int i = 0; i < Constants.TEN; i++) {
            limiter.acquire();
            pool.execute(() -> {
                long cur = System.nanoTime();
                System.out.println((cur - ref.prev) / 1000_000 + "ms");
                ref.prev = cur;
            });
        }
        pool.shutdown();

    }
}

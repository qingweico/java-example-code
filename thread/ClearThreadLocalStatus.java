package thread;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import thread.pool.CustomThreadPool;
import util.constants.Constants;

import java.util.concurrent.ThreadLocalRandom;

/**
 * ThreadLocal适用于变量在线程间隔离,而在方法和类间共享的场景,利用独占资源的方式来解决线程安全问题
 * @see ThreadLocal
 * @see InheritableThreadLocal
 * @see TransmittableThreadLocal
 * @author zqw
 * @date 2022/06/21
 */
@Slf4j
class ClearThreadLocalStatus {
    static CustomThreadPool pool = new CustomThreadPool(1, 1);
    static ThreadLocal<Integer> tl = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < Constants.TEN; i++) {
            pool.execute(() -> {
                try {
                    Integer userId = ThreadLocalRandom.current().nextInt(100);
                    log.info("thread: {}, before userId: {}", Thread.currentThread().getName(), tl.get());
                    tl.set(userId);
                    log.info("thread: {}, after userId: {}", Thread.currentThread().getName(), tl.get());
                } finally {
                    // 需要显示清空ThreadLocal中的数据
                    tl.remove();
                }
            });
        }
        pool.shutdown();
    }
}

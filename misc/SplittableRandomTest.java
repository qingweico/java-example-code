package misc;

import cn.qingweico.concurrent.UnsafeSupport;
import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import java.util.SplittableRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2025/9/3
 */
public class SplittableRandomTest {
    // JCL 不再推荐使用, 使用  SLF4J + Logback 或者 SLF4J + Log4j2
    private static final Log log = LogFactory.getLog(SplittableRandomTest.class);
    private final ExecutorService pool = ThreadPoolBuilder.create();

    @Test
    public void pool() throws InterruptedException {
        SplittableRandom random = new SplittableRandom();

        for (int i = 0; i < 100; i++) {
            pool.execute(() -> {
                SplittableRandom split = random.split();
                log.info(split.nextInt(100));
                UnsafeSupport.shortWait(100);
            });
        }
        pool.shutdown();
        boolean terminated = pool.awaitTermination(10, TimeUnit.SECONDS);
        if (terminated) {
            log.info("所有任务已完成");
        } else {
            log.warn("任务超时, 未完成所有任务");
        }
    }

}

package thread.lock;

import cn.qingweico.concurrent.pool.ThreadPoolBuilder;
import cn.qingweico.supplier.RandomDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2025/9/6
 */
@Slf4j
public class StampedLockTest {
    ExecutorService pool = ThreadPoolBuilder
            .builder()
            .corePoolSize(100)
            .maxPoolSize(100)
            .preStartAllCore(true)
            .isEnableMonitor(true)
            .build();
    private final Point point = new Point();

    @Test
    public void readWrite() throws InterruptedException {
        int rtc = 50, wtc = 10;

        for (int i = 0; i < rtc; i++) {
            pool.execute(() -> {
                for (int j = 0; j < 100; j++) {
                    log.info("distanceFromOrigin: {}", point.distanceFromOrigin());
                    Thread.yield();
                }
            });
        }

        for (int i = 0; i < wtc; i++) {
            pool.execute(() -> {
                double x, y;
                for (int j = 0; j < 100; j++) {
                    x = RandomDataGenerator.rndDouble(10);
                    y = RandomDataGenerator.rndDouble(20);
                    log.info("located X: {}, Y: {}", x, y);
                    point.located(x, y);
                    Thread.yield();
                }
            });
        }

        pool.shutdown();
        boolean termination = pool.awaitTermination(3, TimeUnit.MINUTES);
        if (termination) {
            System.out.println("Complete!");
        }
    }
}

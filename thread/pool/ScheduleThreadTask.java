package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2026/6/16
 * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
 * @see ScheduledExecutorService#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)
 */
@Slf4j
class ScheduleThreadTask {


    public static void main(String[] args) {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(4);
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime nextRun = LocalDate.now()
                .atTime(2, 0);

        if (!now.isBefore(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }
        // 计算当前时间与凌晨两点钟的时间间隔
        long delay = Duration.between(now, nextRun).getSeconds();
        // 每天凌晨两点钟执行
        scheduler.scheduleAtFixedRate(
                () -> log.info("[执行定时任务]"),
                delay,
                TimeUnit.DAYS.toSeconds(1),
                TimeUnit.SECONDS
        );
    }
}

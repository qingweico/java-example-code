package design.behaviour.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2023/10/13
 */
@Slf4j
public final class NonFairSync extends Sync {
    @Override
    boolean initialTryLock() {
        log.info("NonFairSync initialTryLock");
        return false;
    }

    @Override
    protected void tryAcquire() {
        log.info("NonFairSync tryAcquire");
    }
}

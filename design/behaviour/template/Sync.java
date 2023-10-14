package design.behaviour.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zqw
 * @date 2023/10/13
 */
@Slf4j
public abstract class Sync {

    protected void tryAcquire() {
        throw new UnsupportedOperationException();
    }

    /**
     * initialTryLock {@see java.util.concurrent.locks.ReentrantLock.Sync#initialTryLock}
     * @return boolean
     */

    abstract boolean initialTryLock();

    final void lock() {
        log.info("lock");
        if (!initialTryLock()) {
            tryAcquire();
        }
    }

    final void unlock() {
        log.info("unlock");
    }
}

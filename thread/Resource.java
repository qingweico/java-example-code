package thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Primary physical memory
 */
class Resource {

    /**
     * Shared Variable
     * The use of the volatile keyword guarantees thread visibility, but not atomicity.
     */
    int num = 0;
    /**
     * Using Atomic class could guarantee atomicity
     */
    AtomicInteger ai = new AtomicInteger();

    /**
     * auto-increment
     */
    public void addPlusPlus() {
        // num++ is not an atomic operation
        num++;
        ai.getAndIncrement();
    }

}

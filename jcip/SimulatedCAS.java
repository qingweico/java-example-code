package jcip;

import annotation.GuardedBy;
import annotation.ThreadSafe;

/**
 * Simulate the operation of the cas
 * @author:qiming
 * @date: 2021/3/30
 */
@ThreadSafe
public class SimulatedCAS {
    @GuardedBy("this")
    private int value;

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (oldValue == expectedValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int exceptedVale, int newValue) {
        return exceptedVale == compareAndSwap(exceptedVale, newValue);
    }

}

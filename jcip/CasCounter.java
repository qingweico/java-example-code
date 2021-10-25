package jcip;

import annotation.ThreadSafe;

/**
 * Non-blocking counter based on cas implementation
 *
 * @author:qiming
 * @date: 2021/3/30
 */
@ThreadSafe
public class CasCounter {
    private final SimulatedCAS value;

    public CasCounter(SimulatedCAS simulatedCAS) {
        value = simulatedCAS;
    }

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        }
        while (v != value.compareAndSwap(v, v + 1));
        return v + 1;
    }
}

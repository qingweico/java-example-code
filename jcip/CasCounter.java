package jcip;

import annotation.ThreadSafe;

/**
 * Non-blocking counter based on cas implementation
 *
 * @author zqw
 * @date 2021/3/30
 */
@ThreadSafe
class CasCounter {
    private final SimulatedCas value;

    public CasCounter(SimulatedCas simulatedCas) {
        value = simulatedCas;
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

package jcip;

import annotation.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The random number generator based on AtomicInteger
 *
 * @author zqw
 * @date 2021/3/30
 */
@ThreadSafe
class AtomicPseudoRandom extends PseudoRandom {

    private final AtomicInteger seed;

    public AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    public int next(int n) {
        while (true) {
            int s = seed.get();
            int nextSeed = calculateNext(s);
            if (seed.compareAndSet(s, nextSeed)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }
}

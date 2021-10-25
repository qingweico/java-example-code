package jcip;

import annotation.ThreadSafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The random number generator based on AtomicInteger
 *
 * @author:qiming
 * @date: 2021/3/30
 */
@ThreadSafe
public class AtomicPseudoRandom extends PseudoRandom {

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

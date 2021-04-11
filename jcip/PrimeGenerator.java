package jcip;

import annotation.ThreadSafe;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/4/7
 */
@ThreadSafe
public class PrimeGenerator implements Runnable{

    private final List<BigInteger> primes = new ArrayList<>();

    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }
    public void cancel() {
        cancelled = true;
    }
    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }

    /**
     * A prime number generator that runs for only one second
     * @return List<BigInteger>
     * @throws InterruptedException InterruptedException
     */
   static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator generator = new PrimeGenerator();
        new Thread(generator).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        }finally {
            generator.cancel();
        }
        return generator.get();
    }
}

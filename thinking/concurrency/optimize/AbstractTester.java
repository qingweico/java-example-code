package thinking.concurrency.optimize;

import thread.pool.ThreadObjectPool;
import util.collection.Generated;
import util.RandomGenerator;
import util.constants.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


/**
 * Framework to test performance of concurrency containers.
 *
 * @author zqw
 * @date 2021/4/8
 */
abstract class AbstractTester<C> {
    static int testReps = 10;
    static int testCycles = 100;
    static int containerSize = 1000;

    /**
     * containerInitializer
     * @return the value of the container return.
     */
    abstract C containerInitializer();

    /**
     * startReaderAndWriters
     */
    abstract void startReaderAndWriters();
    C testContainer;
    String testId;
    int nReaders;
    int nWriters;
    volatile int readResult = 0;
    volatile long readTime = 0;
    volatile long writeTime = 0;
    CountDownLatch endLatch;
    static ExecutorService exec = ThreadObjectPool.newFixedThreadPool(100);
    Integer[] writeData;
    AbstractTester(String testId, int nReaders, int nWriters) {
        this.testId = testId + " " + nReaders + "r " + nWriters + "w";
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        writeData = Generated.array(Integer.class, new RandomGenerator.Integer(), containerSize);
        for(int i = 0;i < testReps;i++) {
            runTest();
            readTime = 0;
            writeTime = 0;
        }
    }
    void runTest() {
        endLatch = new CountDownLatch(nReaders + nWriters);
        testContainer = containerInitializer();
        startReaderAndWriters();
        try {
            endLatch.await();
        }catch (InterruptedException e) {
            System.out.println("endLatch interrupted");
        }
        System.out.printf("%-27s %14d %14d\n",testId, readTime, writeTime);
        if(readTime != 0 && writeTime != 0) {
            System.out.printf("%-27s %14d\n","readTime + writeTime =", readTime + writeTime);
        }
    }
    abstract class AbstractTestTask implements Runnable {
        /**
         * test
         */
        abstract void test();

        /**
         * putResults
         */
        abstract void putResults();
        long duration;
        @Override
        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;
            synchronized (AbstractTester.this) {
                putResults();
            }
            endLatch.countDown();
        }
    }
    public static void initMain(String[] args) {
        if(args.length > Constants.ZERO) {
            testReps = Integer.parseInt(args[0]);
        }
        if(args.length > Constants.ONE) {
            testCycles = Integer.parseInt(args[1]);
        }
        if(args.length > Constants.TWO) {
            containerSize = Integer.parseInt(args[2]);
        }
        System.out.printf("%-27s %14s %14s\n", "Type", "Read time", "Write time");
    }
}

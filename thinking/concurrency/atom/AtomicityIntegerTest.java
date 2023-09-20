package thinking.concurrency.atom;

import thread.pool.ThreadObjectPool;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.*;

/**
 * @author zqw
 * @date 2021/1/20
 */
class AtomicityIntegerTest implements Runnable {

    private final AtomicInteger i = new AtomicInteger(0);

    public int getValue() {
        return i.get();
    }

    private void evenIncrement() {
        i.addAndGet(2);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                println("Aborting");
                exit(0);
            }
            // Terminate after 2 seconds
        }, 2000);

        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(1, true);
        AtomicityIntegerTest ait = new AtomicityIntegerTest();
        pool.execute(ait);
        while (true) {
            int val = ait.getValue();
            if (val % 2 != 0) {
                println(val);
                exit(0);
                break;
            }
        }
        pool.shutdown();
    }
}

package thinking.concurrency.atom;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.*;

/**
 * @author:qiming
 * @date: 2021/1/20
 */
@SuppressWarnings("InfiniteLoopStatement")
public class AtomicityIntegerTest implements Runnable{

    private final AtomicInteger i = new AtomicInteger(0);
    public int getValue() {
        return i.get();
    }
    private void evenIncrement() {
        i.addAndGet(2);
    }
    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                err("Aborting");
                exit(0);
            }
            // Terminate after 5 seconds
        },5000);

        ExecutorService exec = Executors.newCachedThreadPool();
        AtomicityIntegerTest ait = new AtomicityIntegerTest();
        exec.execute(ait);
        while (true) {
            int val = ait.getValue();
            if(val % 2 != 0){
                print(val);
                exit(0);
            }
        }
    }

}

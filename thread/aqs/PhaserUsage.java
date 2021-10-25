package thread.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/10/17
 */
public class PhaserUsage {
    Phaser phaser = new Phaser();

    ExecutorService exec = Executors.newFixedThreadPool(10);

    class Worker implements Runnable {
        @Override
        public void run() {
            phaser.register();
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("I'm work@" + phaser.getPhase());
                    phaser.arriveAndAwaitAdvance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void run() {
        // main register
        phaser.register();
        exec.execute(new Worker());
        exec.execute(new Worker());
        exec.execute(new Worker());
        exec.execute(new Worker());
        while (true) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("sync..." + phaser.getPhase());
        }
    }

    public static void main(String[] args) {
        var phaser = new PhaserUsage();
        phaser.run();
    }
}

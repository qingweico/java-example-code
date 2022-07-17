package thread.aqs;

import thread.pool.CustomThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * --------------- Phaser ---------------
 * {@link Phaser} 功能与 {@link CountDownLatch} 很接近,但是它允许线程动态注册到
 * {@code Phase}上面,而 {@code CountDownLatch} 不能动态设置
 * {@code Phase} 设计的初衷是实现多个线程类似步骤、阶段场景的协调,线程注册等待屏障条件触发
 * 进而协调彼此间行动
 * <a href="https://www.baeldung.com/java-phaser">java-phaser</a>
 *
 * @author zqw
 * @date: 2021/10/17
 */
class PhaserE {
    Phaser phaser = new Phaser();

    final ExecutorService pool = CustomThreadPool.newFixedThreadPool(2, 5, 1);

    class Worker implements Runnable {
        @Override
        @SuppressWarnings("InfiniteLoopStatement")
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

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        // main register
        phaser.register();
        pool.execute(new Worker());
        pool.execute(new Worker());
        pool.execute(new Worker());
        pool.execute(new Worker());
        while (true) {
            phaser.arriveAndAwaitAdvance();
            System.out.println("sync..." + phaser.getPhase());
        }
    }

    public static void main(String[] args) {
        var phaser = new PhaserE();
        phaser.run();
    }
}

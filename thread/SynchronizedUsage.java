package thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author:qiming
 * @date: 2020/12/18
 */
public class SynchronizedUsage {
    private static final CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        Data data = new Data();
        new Thread(data::f1, "t1").start();
        new Thread(data::f2, "t2").start();

        // Let the t2 thread run first
        new Thread(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.f1();
        },"t1").start();

        new Thread(() -> {
            data.f2();
            latch.countDown();
        }, "t2").start();


    }

}

class Data {
    public synchronized void f1() {
        System.out.println("f1...");
        System.out.println(Thread.currentThread().getName() + "------>" + "线程");
    }

    public synchronized void f2() {
        System.out.println("f2...");
        System.out.println(Thread.currentThread().getName() + "------>" + "线程");
    }
}

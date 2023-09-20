package thinking.concurrency.cooperation;

import thread.pool.ThreadObjectPool;
import util.Print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

/**
 * Basic task cooperation
 * WaxOMatic
 *
 * @author zqw
 * @date 2021/4/11
 */
class WaxMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2);
        pool.execute(new WaxOff(car));
        pool.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        pool.shutdownNow();
    }
}

class Car {
    private boolean waxOn = false;

    public synchronized void waxed() {
        // Ready to buff
        waxOn = true;
        notifyAll();
    }

    public synchronized void buffed() {
        // Ready for another coat of wax
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (!waxOn) {
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn) {
            wait();
        }
    }
}

class WaxOn implements Runnable {
    private final Car car;

    public WaxOn(Car c) {
        car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                print("Wax On! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            Print.println("Exiting via Interrupted");
        }
        Print.println("Ending Wax on task");
    }
}

class WaxOff implements Runnable {
    private final Car car;

    public WaxOff(Car c) {
        car = c;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                car.waitForWaxing();
                print("Wax Off! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        } catch (InterruptedException e) {
            Print.println("Exiting via Interrupted");
        }
        Print.println("Ending Wax off task");
    }
}

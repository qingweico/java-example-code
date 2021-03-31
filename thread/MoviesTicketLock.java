package thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MoviesTicketLock implements Runnable {
    private int ticket = 100;
    private final Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                //lock
                lock.lock();
                if (ticket > 0) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在出售第" + (ticket--) + "张票");
                } else {
                    return;
                }
            } finally {
                //Release the lock
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        MoviesTicketLock s = new MoviesTicketLock();

        Thread t1 = new Thread(s, "窗口一");
        Thread t2 = new Thread(s, "窗口二");
        Thread t3 = new Thread(s, "窗口三");

        t1.start();
        t2.start();
        t3.start();
    }
}

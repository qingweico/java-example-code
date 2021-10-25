package thread;

import java.util.concurrent.TimeUnit;

public class MoviesTicket implements Runnable {

    private static int ticket = 20;

    private int x = 0;

    @Override
    public void run() {
        while (true) {
            if (x % 4 == 0) {
                synchronized (MoviesTicket.class) {
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
                }
            } else {
                sellTicket();
            }
            x++;
        }
    }

    private synchronized void sellTicket() {
        if (ticket > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在出售第" + (ticket--) + "张票");
        }
    }

    public static void main(String[] args) {
        // 创建一个票资源
        MoviesTicket mt = new MoviesTicket();

        // 创建三个线程
        Thread t1 = new Thread(mt, "窗口一");
        Thread t2 = new Thread(mt, "窗口二");
        Thread t3 = new Thread(mt, "窗口三");

        t1.start();
        t2.start();
        t3.start();
    }
}

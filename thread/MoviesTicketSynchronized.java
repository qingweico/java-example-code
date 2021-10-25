package thread;

import java.util.concurrent.TimeUnit;

public class MoviesTicketSynchronized implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在出售第" + (ticket--) + "张票");
                } else {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {

        MoviesTicketSynchronized t = new MoviesTicketSynchronized();

        Thread r1 = new Thread(t, "窗口一");
        Thread r2 = new Thread(t, "窗口二");
        Thread r3 = new Thread(t, "窗口三");

        r1.start();
        r2.start();
        r3.start();
    }
}

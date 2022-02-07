package thread;

import thread.pool.CustomThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2020/1/03
 */
class MoviesTicket implements Runnable {

    private static int ticket = 20;

    private int x = 0;
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(3, 3, 1);


    @Override
    public void run() {
        while (true) {
            if (x % 4 == 0) {
                synchronized (this) {
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
        pool.execute(mt);
        pool.execute(mt);
        pool.execute(mt);
        pool.shutdown();
    }
}

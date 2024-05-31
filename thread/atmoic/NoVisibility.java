package thread.atmoic;


import util.Print;

import java.util.concurrent.TimeUnit;

/**
 * 线程间数据的不可见性
 *
 * @author zqw
 * @date 2021/1/30
 */
class NoVisibility {
    private static boolean ready = false;
    private static int num;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                // remove!! The program will be not ending!
                Thread.yield();
            }
            System.out.println(num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        // 没有使用 volatile 关键字修饰, 理论上来说会无限循环,主线程对变量的修改 其他线程看不到
        // 实际上还是会终止
        // 1 Thread.yield(), 让当前线程让出CPU时间片, 有助于触发内存刷新
        // 2 缓存一致性
        // 3 JVM优化
        num = 42;
        ready = true;
    }
}

package thread.atmoic;


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
            e.printStackTrace();
        }
        num = 42;
        ready = true;
    }
}

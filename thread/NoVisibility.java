package thread;


/**
 * @author:qiming
 * @date: 2021/1/30
 */
public class NoVisibility {
    private static boolean ready = false;
    private static int num;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(num);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReaderThread().start();
        num = 42;
        ready = true;
    }
}

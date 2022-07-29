package thinking.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2021/1/16
 */
public class SimpleDaemon implements Runnable {
    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                TimeUnit.MICROSECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemon());

            // When all non-background threads terminate, the program terminates,
            // killing all background programs in the process. Conversely, the
            // program does not terminate as long as any non-background threads
            // are still running,For example, main is executed by a background process.
            // tips: The setDaemon method must be called before the thread starts to
            // make it a background thread.

            // you must call setDaemon method before thread starting in order to set it
            // up as a background thread.
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        // After 1s, the main method ends and the background program will stop.
        TimeUnit.SECONDS.sleep(1);
    }
}

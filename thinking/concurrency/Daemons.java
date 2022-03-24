package thinking.concurrency;


import java.util.concurrent.TimeUnit;

/**
 * Daemon threads spawn daemon threads
 *
 * @author zqw
 * @date 2021/1/16
 */
public class Daemons {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread(new Daemon());
        t.setDaemon(true);
        t.start();
        System.out.println("d.isDaemon() = " + t.isDaemon() + ". ");
        // Allow the daemon threads to finish their startup process.
        TimeUnit.SECONDS.sleep(2);
    }

}

class Daemon implements Runnable {
    private final Thread[] t = new Thread[10];

    @Override
    public void run() {
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new DaemonSpawn());
            t[i].start();
            System.out.println("Daemon " + i + " started");
        }
        for (int i = 0; i < t.length; i++) {
            System.out.println("t[" + i + "].isDaemon() = " + t[i].isDaemon() + ". ");
        }
        while (true) {
            Thread.yield();
        }
    }
}

class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while (true) {
            Thread.yield();
        }
    }
}

class DaemonDontRunFinally {
    public static void main(String[] args) {
        Thread t = new Thread(new ADaemon());
        // t.setDaemon(true);
        t.start();
    }
}

class ADaemon implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via Interruption");
        } finally {
            System.out.println("This should always run?");
        }
    }
}

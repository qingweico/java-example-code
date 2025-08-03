package thinking.concurrency;


import cn.qingweico.concurrent.pool.ThreadPoolBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Daemon threads spawn daemon threads
 *
 * @author zqw
 * @date 2021/1/16
 */
class Daemons {
    public static void main(String[] args) throws Exception {
        ExecutorService single = ThreadPoolBuilder.single(true);
        single.execute(new Daemon());
        // Allow the daemon threads to finish their startup process.
        TimeUnit.SECONDS.sleep(2);
        single.shutdown();
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
        ExecutorService single = ThreadPoolBuilder.single(false);
        single.execute(new DaemonThread());
        single.shutdown();
    }
}

class DaemonThread implements Runnable {

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

package thinking.concurrency;

import cn.qingweico.io.Print;

/**
 * Understand join()
 *
 * @author zqw
 * @date 2021/1/29
 */
public class Joining {
    public static void main(String[] args) throws InterruptedException {
        Sleeper sleeper = new Sleeper("Sleeper", 1500),
                grumpy = new Sleeper("Grumpy", 1500);

        Joiner dopey = new Joiner("Dopey", sleeper),
                doc = new Joiner("Doc", sleeper);
        grumpy.interrupt();

    }
}

class Sleeper extends Thread {
    private final int duration;

    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            Print.println(getName() + " was interrupted. "
                    + "isInterrupted(): " + isInterrupted());
            return;
        }
        Print.println(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private final Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            // Timeout parameter: default 0 millis
            // The Joiner thread will be suspended until the Sleep thread ends.
            sleeper.join();
        } catch (InterruptedException e) {
            Print.println("Interrupted");
        }
        Print.println(getName() + " join completed");
    }
}

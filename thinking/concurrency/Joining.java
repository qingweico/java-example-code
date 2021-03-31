package thinking.concurrency;

import static util.Print.print;

/**
 * Understand join()
 *
 * @author:周庆伟
 * @date: 2021/1/29
 */
public class Joining {
    public static void main(String[] args) {
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

    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            print(getName() + " was interrupted. "
                    + "isInterrupted(): " + isInterrupted());
            return;
        }
        print(getName() + " has awakened");
    }
}

class Joiner extends Thread {
    private final Sleeper sleeper;

    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            print("Interrupted");
        }
        print(getName() + " join completed");
    }
}

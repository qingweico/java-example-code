package thinking.concurrency;

import static util.Print.print;


/**
 * @author:qiming
 * @date: 2021/1/18
 */
//TODO
public class ThreadVariations {

}

class InnerThread {
    private int countDown = 5;

    class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }

        public void run() {
            try {
                while (true) {
                    print(this);
                    if (--countDown == 0) return;
                    sleep(10);
                }

            } catch (InterruptedException e) {
                print("interrupted");
            }
        }

        public String toString() {
            return getName() + ": " + countDown;

        }
    }

    public InnerThread(String name) {
        Inner inner = new Inner(name);
    }
}

class InnerThread0 {
    private int countDown = 5;

    public InnerThread0(String name) {
        Thread t = new Thread(name) {
            @Override
            public void run() {
                try {
                    while (true) {
                        print(this);
                        if (--countDown == 0) return;
                        sleep(10);
                    }
                } catch (InterruptedException e) {
                    print("sleep() interrupted");
                }
            }

            public String toString() {
                return getName() + ": " + countDown;

            }
        };
        t.start();

    }

}

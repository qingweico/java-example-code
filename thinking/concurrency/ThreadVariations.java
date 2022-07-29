package thinking.concurrency;

import java.util.concurrent.TimeUnit;

import static util.Print.print;


/**
 * create threads with inner class
 *
 * @author zqw
 * @date 2021/1/18
 */
class ThreadVariations {
    public static void main(String[] args) {
        new InnerThread("InnerThread");
        new InnerThread0("InnerThread0");
        new InnerRunnable("InnerRunnable");
        new InnerRunnable0("InnerRunnable0");
        new ThreadMethod("ThreadMethod").runTask();
    }

}

// Using a named inner class:
class InnerThread {
    private int countDown = 5;

    class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    print(this);
                    if (--countDown == 0) {
                        return;
                    }
                    sleep(10);
                }

            } catch (InterruptedException e) {
                print("interrupted");
            }
        }

        @Override
        public String toString() {
            return getName() + ": " + countDown;

        }
    }

    public InnerThread(String name) {
        new Inner(name);
    }
}

// Using an anonymous inner class
class InnerThread0 {
    private int countDown = 5;

    public InnerThread0(String name) {
        Thread t = new Thread(name) {
            @Override
            public void run() {
                try {
                    while (true) {
                        print(this);
                        if (--countDown == 0) {
                            return;
                        }
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

// Using a named Runnable implementation:
class InnerRunnable {
    private int countDown = 5;
    private Inner inner;

    private class Inner implements Runnable {
        Thread t;

        Inner(String name) {
            t = new Thread(this, name);
            t.start();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    print(this);
                    if (--countDown == 0) {
                        return;
                    }
                    TimeUnit.MILLISECONDS.sleep(10);
                }
            } catch (InterruptedException e) {
                print("sleep() interrupted");
            }
        }

        public String toString() {
            return t.getName() + ": " + countDown;
        }
    }

    public InnerRunnable(String name) {
        inner = new Inner(name);
    }
}

// Using an anonymous Runnable implementation:
class InnerRunnable0 {
    private int countDown = 5;
    private Thread t;

    public InnerRunnable0(String name) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        print(this);
                        if (--countDown == 0) {
                            return;
                        }
                        TimeUnit.MILLISECONDS.sleep(10);
                    }
                } catch (InterruptedException e) {
                    print("sleep() interrupted");
                }
            }

            public String toString() {
                return Thread.currentThread().getName() + ": " + countDown;
            }
        }, name);
        t.start();
    }
}

// A separated method to run some code as a task:
class ThreadMethod {
    private int countDown = 5;
    private Thread t;
    private String name;

    public ThreadMethod(String name) {
        this.name = name;
    }

    public void runTask() {
        if (t == null) {
            t = new Thread(name) {
                public void run() {
                    try {
                        while (true) {
                            print(this);
                            if (--countDown == 0) {
                                return;
                            }
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
}

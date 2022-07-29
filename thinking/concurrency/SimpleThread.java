package thinking.concurrency;

/**
 * Direct Inheriting Thread
 *
 * @author zqw
 * @date 2021/1/17
 */
class SimpleThread extends Thread {
    private int countDown = 5;
    private static int threadCount = 0;

    public SimpleThread() {
        // Store the thread name
        super(Integer.toString(++threadCount));
        start();
    }

    @Override
    public String toString() {
        return "#" + getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new SimpleThread();
        }
    }
}

class SelfManaged implements Runnable {
    private int countDown = 5;

    public SelfManaged() {
        // Starting threads in the constructor can become problematic,
        // This is why the Executor is preferred rather than the Thread
        // object being created explicitly.
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true){
            System.out.print(this);
            if(--countDown == 0) {
                return;
            }
            ;
        }
    }

    public static void main(String[] args) {
        for(int i = 0;i < 5;i++){
            new SelfManaged();
        }
    }
}

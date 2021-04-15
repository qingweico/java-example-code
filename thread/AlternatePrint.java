package thread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.prints;

/**
 * Alternate print ABCDEF 123456
 *
 * @author:qiming
 * @date: 2021/4/15
 */
public class AlternatePrint {
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    private static final char[] numbers = "123456".toCharArray();
    private static final char[] words = "ABCDEF".toCharArray();


    // Using AtomicInteger
    static void AtomicPrint() {
        AtomicInteger threadNo = new AtomicInteger(1);

        new Thread(() ->
        {
            for (char n : numbers) {
                while (threadNo.get() != 1) {
                }
                prints(n);
                threadNo.set(2);
            }
        }, "t1").start();
        new Thread(() -> {
            for (char c : words) {
                while (threadNo.get() != 2) {
                }
                prints(c);
                threadNo.set(1);
            }
        }, "t2").start();
    }

    // Using BlockQueue
    static void BlockingQueuePrint() {
        BlockingQueue<String> q1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> q2 = new ArrayBlockingQueue<>(1);

        new Thread(() -> {
            for (char n : numbers) {
                prints(n);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        new Thread(() -> {
            for (char c : words) {
                try {
                    q1.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                prints(c);
                try {
                    q2.put("ok");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Using CAS
    static void CasPrint() {
        char[] numbers = "123456".toCharArray();
        char[] words = "ABCDEF".toCharArray();

        new Thread(() ->
        {
            for (char n : numbers) {
                while (r != ReadyToRun.T1) {
                }
                prints(n);
                r = ReadyToRun.T2;
            }
        }, "t1").start();

        new Thread(() -> {
            for (char w : words) {
                while (r != ReadyToRun.T2) {
                }
                prints(w);
                r = ReadyToRun.T1;
            }
        }, "t2").start();
    }

    // Using LockCondition
    static void LockConditionPrint() {
        Lock lock = new ReentrantLock();
        Condition conditionT1 = lock.newCondition();
        Condition conditionT2 = lock.newCondition();

        getThread(lock, conditionT2, conditionT1, numbers).start();
        getThread(lock, conditionT1, conditionT2, words).start();
    }

    static Thread getThread(Lock lock, Condition conditionT1, Condition conditionT2, char[] arr) {
        return new Thread(() -> {
            try {
                lock.lock();
                for (char c : arr) {
                    prints(c);
                    conditionT1.signal();
                    conditionT2.await();
                }
                conditionT1.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t2");
    }

    // Using LockSupport
    static Thread t1 = null, t2 = null;

    static void LockSupportPrint() {

        t1 = new Thread(() ->
        {
            for (char n : numbers) {
                prints(n);
                // Wake up the t2 thread
                LockSupport.unpark(t2);
                // Blocking the current thread
                LockSupport.park();
            }
        }, "t1");
        t2 = new Thread(() ->
        {
            for (char w : words) {
                // Blocking the current thread
                LockSupport.park();
                prints(w);
                // Wake up the t1 thread
                LockSupport.unpark(t1);
            }
        }, "t2");


        t1.start();
        t2.start();
    }

    static void PipedStreamPrint() throws IOException {

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String message = "Your turn";

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char n : numbers) {
                    input1.read(buffer);
                    if (new String(buffer).equals(message)) {
                        prints(n);
                    }
                    output1.write(message.getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : words) {
                    prints(c);
                    output2.write(message.getBytes());
                    input2.read(buffer);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }

    public static void main(String[] args) throws IOException {
        AtomicPrint();
        BlockingQueuePrint();
        CasPrint();
        LockConditionPrint();
        LockSupportPrint();
        PipedStreamPrint();
    }
}

package thread;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.prints;

/**
 * Alternate print ABCDEF 123456
 * output: 1A2B3C4D5E6F
 *
 * @author:qiming
 * @date: 2021/4/15
 */
@SuppressWarnings("all")
public class AlternatePrint {
    enum ReadyToRun {T1, T2}

    static volatile ReadyToRun r = ReadyToRun.T1;

    private static final CountDownLatch terminated = new CountDownLatch(2);

    private static final char[] numbers = "123456".toCharArray();
    private static final char[] chars = "ABCDEF".toCharArray();


    // Using AtomicInteger
    @Test
    public void AtomicPrint() {
        AtomicInteger threadNo = new AtomicInteger(1);

        new Thread(() ->
        {
            for (char n : numbers) {

                while (threadNo.get() != 1) {
                    System.out.println("cas");
                }
                prints(n);
                threadNo.set(2);
            }
            terminated.countDown();
        }).start();
        new Thread(() -> {
            for (char c : chars) {
                while (threadNo.get() != 2) {
                    System.out.println("cas");
                }
                prints(c);
                threadNo.set(1);
            }
            terminated.countDown();
        }).start();
        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Using BlockQueue
    @Test
    public void BlockingQueuePrint() {
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
            terminated.countDown();
        }).start();
        new Thread(() -> {
            for (char c : chars) {
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
            terminated.countDown();
        }).start();

        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Using CAS
    @Test
    public void CasPrint() {
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
            terminated.countDown();
        }).start();

        new Thread(() -> {
            for (char w : words) {
                while (r != ReadyToRun.T2) {
                }
                prints(w);
                r = ReadyToRun.T1;
            }
            terminated.countDown();
        }).start();

        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    // Using LockCondition
    @Test
    public void LockConditionPrint() {
        Lock lock = new ReentrantLock();
        Condition conditionT1 = lock.newCondition();
        Condition conditionT2 = lock.newCondition();

        getThread(lock, conditionT2, conditionT1, numbers).start();
        getThread(lock, conditionT1, conditionT2, chars).start();
        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                terminated.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
    }

    // Using LockSupport
    static Thread t1 = null, t2 = null;

    @Test
    public void LockSupportPrint() {

        t1 = new Thread(() ->
        {
            for (char n : numbers) {
                prints(n);
                // Wake up the t2 thread
                LockSupport.unpark(t2);
                // Blocking the current thread
                LockSupport.park();
            }
            terminated.countDown();
        }, "t1");
        t2 = new Thread(() ->
        {
            for (char w : chars) {
                // Blocking the current thread
                LockSupport.park();
                prints(w);
                // Wake up the t1 thread
                LockSupport.unpark(t1);
            }
            terminated.countDown();
        }, "t2");


        t1.start();
        t2.start();

        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PipedStreamPrint() throws IOException {

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
                terminated.countDown();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            byte[] buffer = new byte[9];
            try {
                for (char c : chars) {
                    prints(c);
                    output2.write(message.getBytes());
                    input2.read(buffer);

                }
                terminated.countDown();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Using TransferQueue
    @Test
    public void transferQueuePrint() {

        TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                for (char n : numbers) {
                    prints(transferQueue.take());
                    transferQueue.transfer(n);
                }
                terminated.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (char c : chars) {
                    transferQueue.transfer(c);
                    prints(transferQueue.take());
                }
                terminated.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            terminated.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

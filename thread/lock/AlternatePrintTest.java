package thread.lock;

import org.junit.Test;
import thread.pool.ThreadObjectPool;
import util.Print;
import util.constants.Constants;

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
 * Print: 1A2B3C4D5E6F
 *
 * @author zqw
 * @date 2021/4/15
 */
public class AlternatePrintTest {

    enum ReadyToRun {
        /**
         * the sign of t1 thread run
         */
        T1,
        /**
         * the sign of t2 thread run
         */
        T2
    }

    static volatile ReadyToRun r = ReadyToRun.T1;

    private final CountDownLatch terminated = new CountDownLatch(Constants.TWO);

    private final ExecutorService pool = ThreadObjectPool.newFixedThreadPool(2, 2, 1);

    private final char[] numbers = "123456".toCharArray();
    private final char[] chars = "ABCDEF".toCharArray();

    @Test
    public void atomicPrint() {
        AtomicInteger threadNo = new AtomicInteger(Constants.ONE);

        pool.execute(() ->
        {
            for (char n : numbers) {

                for (; ; ) {
                    if (threadNo.get() == Constants.ONE) {
                        break;
                    }
                }
                prints(n);
                threadNo.set(Constants.TWO);
            }
            terminated.countDown();
        });
        pool.execute(() -> {
            for (char c : chars) {
                for (; ; ) {
                    if (threadNo.get() == Constants.TWO) {
                        break;
                    }
                }
                prints(c);
                threadNo.set(Constants.ONE);
            }
            terminated.countDown();
        });
        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    @Test
    public void blockingQueuePrint() {
        BlockingQueue<String> q1 = new ArrayBlockingQueue<>(Constants.ONE);
        BlockingQueue<String> q2 = new ArrayBlockingQueue<>(Constants.ONE);

        pool.execute(() -> {
            for (char n : numbers) {
                prints(n);
                try {
                    q1.put("ok");
                    q2.take();
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }

            }
            terminated.countDown();
        });
        pool.execute(() -> {
            for (char c : chars) {
                try {
                    q1.take();
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }
                prints(c);
                try {
                    q2.put("ok");
                } catch (InterruptedException e) {
                    Print.err(e.getMessage());
                }
            }
            terminated.countDown();
        });

        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    @Test
    public void casPrint() {
        pool.execute(() -> {
            for (char w : chars) {
                for (; ; ) {
                    if (r == ReadyToRun.T2) {
                        break;
                    }
                }
                prints(w);
                r = ReadyToRun.T1;
            }
            terminated.countDown();
        });
        pool.execute(() ->
        {
            for (char n : numbers) {
                for (; ; ) {
                    if (r == ReadyToRun.T1) {
                        break;
                    }
                }
                prints(n);
                r = ReadyToRun.T2;
            }
            terminated.countDown();
        });

        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    private final CountDownLatch run = new CountDownLatch(1);

    @Test
    public void lockConditionPrint() {
        Lock lock = new ReentrantLock();
        // numbers WaitSet
        Condition nc = lock.newCondition();
        // chars WaitSet
        Condition cc = lock.newCondition();

        pool.execute(() -> {
            lock.lock();
            try {
                for (int i = 0; i < numbers.length; i++) {
                    prints(numbers[i]);
                    if (i == 0) {
                        run.countDown();
                    }
                    cc.signal();
                    nc.await();
                }
                cc.signal();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                lock.unlock();
                terminated.countDown();
            }
        });
        pool.execute(() -> {
            try {
                run.await();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            }
            lock.lock();
            try {
                for (char c : chars) {
                    prints(c);
                    nc.signal();
                    cc.await();
                }
                nc.signal();
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                lock.unlock();
                terminated.countDown();
            }
        });
        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    static Thread t1 = null, t2 = null;

    @Test
    public void lockSupportPrint() {

        pool.execute(() ->

        {
            t1 = Thread.currentThread();
            for (char n : numbers) {
                prints(n);
                // Wake up the t2 thread
                LockSupport.unpark(t2);
                // Blocking the current thread
                LockSupport.park();
            }
            terminated.countDown();
        });
        pool.execute(() ->
        {
            t2 = Thread.currentThread();
            for (char w : chars) {
                // Blocking the current thread
                LockSupport.park();
                prints(w);
                // Wake up the t1 thread
                LockSupport.unpark(t1);
            }
            terminated.countDown();
        });

        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    @Test
    @SuppressWarnings("all")
    public void pipedStreamPrint() throws IOException {

        PipedInputStream input1 = new PipedInputStream();
        PipedInputStream input2 = new PipedInputStream();
        PipedOutputStream output1 = new PipedOutputStream();
        PipedOutputStream output2 = new PipedOutputStream();

        input1.connect(output2);
        input2.connect(output1);

        String message = "Your turn";
        int bufferSize = Constants.KB;
        pool.execute(() -> {
            byte[] buffer = new byte[bufferSize];
            try {
                for (char n : numbers) {
                    prints(n);
                    output2.write(message.getBytes());
                    input2.read(buffer);
                }
            } catch (IOException e) {
                Print.err(e.getMessage());
            } finally {
                terminated.countDown();
            }
        });
        pool.execute(() -> {
            byte[] buffer = new byte[bufferSize];
            try {
                for (char c : chars) {
                    int read = input1.read(buffer);
                    if (new String(buffer, 0, read).equals(message)) {
                        prints(c);
                    }
                    output1.write(message.getBytes());
                }
            } catch (IOException e) {
                Print.err(e.getMessage());
            } finally {
                terminated.countDown();
            }
        });

        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }

    @Test
    public void transferQueuePrint() {

        TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();

        pool.execute(() -> {
            try {
                for (char n : numbers) {
                    transferQueue.transfer(n);
                    prints(transferQueue.take());
                }
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                terminated.countDown();
            }
        });

        pool.execute(() -> {
            try {
                for (char c : chars) {
                    prints(transferQueue.take());
                    transferQueue.transfer(c);
                }
            } catch (InterruptedException e) {
                Print.err(e.getMessage());
            } finally {
                terminated.countDown();
            }
        });

        try {
            terminated.await();
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        pool.shutdown();
    }
}

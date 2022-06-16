package thread.cas;

import org.junit.Test;
import thinking.genericity.LinkedStack;
import thread.pool.CustomThreadPool;
import util.Constants;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zqw
 * @date 2021/10/1
 */
public class LockFreeStackTest<T> {
    AtomicStampedReference<Node> headRef;
    Node head;
    static int initialValue = Constants.ZERO;
    static AtomicInteger counter = new AtomicInteger(initialValue);
    static AtomicInteger casCount = new AtomicInteger(initialValue);
    static ExecutorService pool = CustomThreadPool.newFixedThreadPool(10, 100, 100);
    public LockFreeStackTest() {
        head = new Node();
        this.headRef = new AtomicStampedReference<>(head, initialValue);
    }

    private class Node {
        T value;
        Node next;

        Node() {
            this.value = null;
            this.next = null;
        }

        Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        boolean end() {
            return value == null && next == null;
        }
    }

    public void push(T value) {
        int stamp;
        int i = 1;
        Node headNode;
        do {
            if (i-- <= 0) {
                casCount.getAndIncrement();
                System.out.println("push cas...");
            }
            stamp = headRef.getStamp();
            headNode = headRef.getReference();
            head = new Node(value, headNode);
        } while (!headRef.compareAndSet(headNode, head, stamp, stamp + 1));

    }

    public T pop() {
        int stamp;
        int i = 1;
        Node headNode;
        T result;
        do {
            if (i-- <= 0) {
                casCount.getAndIncrement();
                System.out.println("pop cas...");
            }
            stamp = headRef.getStamp();
            headNode = headRef.getReference();
            result = headNode.value;

            if (!headNode.end()) {
                head = headNode.next;
            }
        } while (!headRef.compareAndSet(headNode, head, stamp, stamp + 1));
        return result;
    }

    @Test
    public void noCas() {
        var stack = new LinkedStack<Integer>();
        var list = IntStream.range(0, 100)
                .mapToObj(i -> {
                    var t = new Thread(() -> {
                        for (int j = 0; j < Constants.TEN; j++) {
                            try {
                                stack.push(j);
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    t.start();
                    return t;
                }).collect(Collectors.toList());
        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        int count = 0;
        while (stack.pop() != null) {
            count++;
        }
        System.out.format("c = %d", count);
    }

    @Test
    public void lockFree() {
        var stack = new LockFreeStackTest<Integer>();
        var pushLatch = new CountDownLatch(100);
        var popLatch = new CountDownLatch(100);
        for (int i = 0; i < Constants.HUNDRED; i++) {
            pool.execute(() -> {
                for (int j = 0; j < Constants.TEN; j++) {
                    stack.push(j);
                }
                pushLatch.countDown();
            });
        }
        try {
            pushLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < Constants.HUNDRED; i++) {
            pool.execute(() -> {
                while (stack.pop() != null) {
                    counter.getAndIncrement();
                }
                popLatch.countDown();
            });
        }
        try {
            popLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.format("counter = %d\n", counter.get());
        System.out.format("casCount = %d", casCount.get());
    }

}

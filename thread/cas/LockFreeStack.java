package thread.cas;

import org.junit.Test;
import thinking.genericity.LinkedStack;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author:qiming
 * @date: 2021/10/1
 */
public class LockFreeStack<T> {
    AtomicStampedReference<Node> headRef;
    Node head;
    static AtomicInteger counter = new AtomicInteger(0);
    static AtomicInteger casCount = new AtomicInteger(0);

    public LockFreeStack() {
        head = new Node();
        this.headRef = new AtomicStampedReference<>(head, 0);
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
                        for (int j = 0; j < 10; j++) {
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
        var stack = new LockFreeStack<Integer>();
        var pushLatch = new CountDownLatch(100);
        var popLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    stack.push(j);
                }
                pushLatch.countDown();
            }).start();
        }
        try {
            pushLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                while (stack.pop() != null) {
                    counter.getAndIncrement();
                }
                popLatch.countDown();
            }).start();
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

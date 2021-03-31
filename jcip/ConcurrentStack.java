package jcip;

import annotation.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Non-blocking stack
 *
 * @author:qiming
 * @date: 2021/3/30
 */
@ThreadSafe
public class ConcurrentStack<E> {

    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node<>(item);
        Node<E> oldHead;
        do {
            oldHead = top.get();
            // Head interpolation
            newHead.next = oldHead;
        }
        while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = top.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        }while(!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node<E> {
        public final E item;
        public Node<E> next;

        public Node(E e) {
            item = e;
        }

    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(Float.floatToIntBits(34.4f));
        ;
    }

}

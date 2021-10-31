package algorithm.list;

import org.junit.Test;

import java.util.NoSuchElementException;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public class LinkedList<E> {

    private final Node dummy;
    private int size;

    private class Node {
        E e;
        Node next;

        Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    public LinkedList() {
        dummy = new Node();
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index = " + index);
        }
        Node prev = dummy;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        prev.next = new Node(e, prev.next);
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E get(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index = " + index);
        }
        Node cur = dummy.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    public void set(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index = " + index);
        }
        Node cur = dummy.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.e = e;
    }

    public boolean contains(E e) {
        Node cur = dummy.next;
        while (cur != null) {
            if (cur.e.equals(e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index = " + index);
        }
        Node prev = dummy;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node cur = prev.next;
        prev.next = cur.next;
        cur.next = null;
        size--;
        return cur.e;
    }

    public void removeElement(E e) {
        Node prev = dummy;
        while (prev.next != null) {
            if (prev.next.e.equals(e)) {
                break;
            }
            prev = prev.next;
        }
        if (prev.next != null) {
            Node cur = prev.next;
            prev.next = cur.next;
            cur.next = null;
            size--;
        } else {
            throw new NoSuchElementException();
        }
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node cur = dummy.next;
        while (cur != null) {
            res.append(cur.e);
            res.append("->");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }

    public LinkedList<Integer>.Node reverseList(LinkedList<Integer>.Node head) {
        if (head == null) return null;
        LinkedList<Integer>.Node cur = head;
        LinkedList<Integer>.Node p = null;
        while (cur != null) {
            LinkedList<Integer>.Node next = cur.next;
            cur.next = p;
            p = cur;
            cur = next;
        }
        return p;
    }

    public LinkedList<Integer>.Node reverseListR(LinkedList<Integer>.Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        LinkedList<Integer>.Node rev = reverseListR(head.next);
        head.next.next = head;
        head.next = null;
        return rev;
    }

    @Test
    public void testRev() {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.addLast(i);
        }
        print(linkedList);
        linkedList.dummy.next = reverseList(linkedList.dummy.next);
        print(linkedList);
        linkedList.dummy.next = reverseListR(linkedList.dummy.next);
        print(linkedList);
    }
}

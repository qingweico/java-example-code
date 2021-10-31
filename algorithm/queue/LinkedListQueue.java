package algorithm.queue;

/**
 * @author:qiming
 * @date: 2021/10/31
 */
public class LinkedListQueue<E> implements Queue<E> {
    private class Node {
        E e;
        Node next;

        Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        Node(E e) {
            this(e, null);
        }

        Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        this.head = this.tail = null;
        this.size = 0;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(E e) {
        if (tail == null) {
            tail = new Node(e);
            head = tail;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            return null;
        }
        Node cur = head;
        head = head.next;
        cur.next = null;
        if (head == null) {
            tail = null;
        }
        return cur.e;
    }

    @Override
    public E front() {
        if (isEmpty()) {
            return null;
        }
        return head.e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node cur = head;
        res.append("LinkedListQueue front ->");
        while (cur != null) {
            res.append(cur.e);
            res.append("->");
            cur = cur.next;
        }
        res.append("NULL");
        return res.toString();
    }
}

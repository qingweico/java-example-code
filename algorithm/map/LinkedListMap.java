package algorithm.map;

import java.util.NoSuchElementException;

/**
 * @author:qiming
 * @date: 2021/10/30
 */
public class LinkedListMap<K, V> implements Map<K, V> {

    private final Node dummy;
    private int size;

    public LinkedListMap() {
        dummy = new Node();
        size = 0;
    }

    private class Node {
        K key;
        V value;
        Node next;

        public Node(K k, V v, Node next) {
            this.key = k;
            this.value = v;
            this.next = next;
        }
        public Node() {
            this(null, null, null);
        }

        @Override
        public String toString() {
            return key.toString() + " : " + value.toString();
        }
    }

    @Override
    public void add(K k, V v) {
        Node node = getNode(k);
        if (node == null) {
            dummy.next = new Node(k, v, dummy.next);
            size++;
        } else {
            node.value = v;
        }
    }

    @Override
    public V remove(K k) {
        Node prev = dummy;
        while (prev.next != null) {
            if (prev.next.key.equals(k)) {
                break;
            }
            prev = prev.next;
        }
        if (prev.next != null) {
            Node cur = prev.next;
            prev.next = cur.next;
            cur.next = null;
            size--;
            return cur.value;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(K k) {
        return getNode(k) != null;
    }

    @Override
    public V get(K k) {
        Node node = getNode(k);
        return node == null ? null : node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void set(K k, V v) {
        Node node = getNode(k);
        if (node == null) {
            throw new NoSuchElementException();
        }
        node.value = v;
    }

    private Node getNode(K k) {
        Node cur = dummy.next;
        while (cur != null) {
            if (cur.key.equals(k)) {
                return cur;
            }
            cur = cur.next;
        }
        return null;
    }
}

package collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:qiming
 * @date: 2021/11/19
 */
public class LruCache<K, V> {

    class Node {
        K k;
        V v;
        Node next, prev;

        Node() {
            this.next = null;
            this.prev = null;
        }

        Node(K k, V v) {
            this();
            this.k = k;
            this.v = v;
        }
    }

    private final Map<K, Node> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final Node head;
    private final Node tail;

    public LruCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    private void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        remove(node);
        addFirst(node);
    }

    private Node removeLast() {
        Node old = tail.prev;
        remove(old);
        return old;
    }

    public void put(K k, V v) {
        Node node = cache.get(k);
        if (node == null) {
            Node newNode = new Node(k, v);
            cache.put(k, newNode);
            addFirst(newNode);
            ++size;
            if (size > capacity) {
                Node tail = removeLast();
                cache.remove(tail.k);
                size--;
            }
        } else {
            node.v = v;
            moveToHead(node);
        }
    }

    public V get(K k) {
        Node node = cache.get(k);
        if (node == null) {
            return null;
        } else {
            moveToHead(node);
            return node.v;
        }
    }

    public static void main(String[] args) {

       // TODO
        var lru = new LruCache<String, String>(3);
        lru.put("A", "1");
        lru.put("B", "2");
        lru.put("C", "3");
        lru.get("A");
        lru.put("D", "4");
        lru.get("C");
        lru.put("E", "5");
    }
}

package collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author zqw
 * @date 2021/11/19
 */
public class LruCache<K, V> implements Iterable<K> {

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
        node.next = null;
        node.prev = null;
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

    @Override
    public Iterator<K> iterator() {

        return new Iterator<>() {
            private Node cur = head.next;

            @Override
            public boolean hasNext() {
                return cur != tail;
            }

            @Override
            public K next() {
                Node node = cur;
                cur = cur.next;
                return node.k;
            }
        };
    }


    public static void main(String[] args) {

        var lru = new LruCache<String, String>(3);
        lru.put("A", "1");
        lru.put("B", "2");
        lru.put("C", "3");
        lru.get("A");
        lru.put("D", "4");
        lru.get("C");
        lru.put("E", "5");
        LruCache<String, String>.Node cur = lru.head.next;
        System.out.print("[head<-");
        while (cur != lru.tail) {
            System.out.print(cur.k + "<-");
            cur = cur.next;
        }
        System.out.println("tail]");
    }
}

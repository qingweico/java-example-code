package algorithm.map;

/**
 * @author zqw
 * @date 2021/12/6
 */
public class HashMap<K, V> {
    static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        Node(K key, V value, Node<K, V> node) {
            this.next = node.next;
            this.key = key;
            this.value = value;
        }

        @Override
        public final String toString() {
            return key + "=" + value;
        }
    }

    static final int DEFAULT_CAPACITY = 1 << 4;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    int threshold;
    Node<K, V>[] table;
    int size;
    int capacity;

    public HashMap() {
        this.threshold = (int) (DEFAULT_CAPACITY * DEFAULT_LOAD_FACTOR);
        this.capacity = DEFAULT_CAPACITY;
        size = 0;
    }

    public HashMap(int capacity) {
        this.threshold = (int) (capacity * DEFAULT_LOAD_FACTOR);
        this.capacity = capacity;
        size = 0;
    }

    private int getIndex(K k, Node<K, V>[] tab) {
        int hash = k.hashCode();
        int index = hash % tab.length;
        return Math.abs(index);
    }

    public V put(K key, V value) {
        return putVal(key, value, table);
    }

    private V putVal(K key, V value, Node<K, V>[] tab) {
        int index = getIndex(key, tab);
        if (table == null || table.length == 0) {
            resize();
        }
        Node<K, V> node = tab[index];
        if (node == null) {
            table[index] = new Node<>(key, value);
            size++;
            return null;
        }
        while (node != null) {
            if (node.hashCode() == key.hashCode() && node.key == key) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
            node = node.next;
        }
        Node<K, V> newNode = new Node<>(key, value, table[index]);
        table[index] = newNode;
        size++;
        if (size > threshold) {
            resize();
        }
        return null;
    }

    private void resize() {
        @SuppressWarnings("unchecked")
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[capacity];
        rehash(newTab);
        table = newTab;
    }

    private void rehash(Node<K, V>[] tab) {
        size = 0;
        for (Node<K, V> kvNode : table) {
            if (kvNode == null) {
                continue;
            }
            Node<K, V> node = kvNode;
            while (node != null) {
                putVal(node.key, node.value, tab);
                node = node.next;
            }
        }
    }

    public V get(K key) {
        int index = getIndex(key, table);
        Node<K, V> node = table[index];
        while (node != null) {
            if (node.key.hashCode() == key.hashCode() &&
                    node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{");
        for (int i = 0; i < table.length; i++) {
            res.append(table[i]);

            if (i != table.length - 1) {
                res.append(", ");
            }
        }
        res.append("}");
        return res.toString();
    }
}

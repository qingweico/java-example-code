package algorithm.map;

import java.util.NoSuchElementException;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class BinarySearchTreeMap<K extends Comparable<K>, V> implements Map<K, V> {
    private Node root;
    private int size;

    public BinarySearchTreeMap() {
        root = null;
        size = 0;
    }

    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K k, V v) {
            this.key = k;
            this.value = v;
            left = null;
            right = null;
        }
    }

    @Override
    public void add(K k, V v) {
        root = add(root, k, v);
    }

    private Node add(Node node, K k, V v) {
        if (node == null) {
            size++;
            return new Node(k, v);
        }
        if (k.compareTo(node.key) < 0) {
            node.left = add(node.left, k, v);
        } else if (k.compareTo(node.key) > 0) {
            node.right = add(node.right, k, v);
        } else {
            node.value = v;
        }
        return node;
    }

    private Node getNode(Node node, K k) {
        if (node == null) {
            return null;
        }
        if (k.compareTo(node.key) == 0) {
            return node;
        } else if (k.compareTo(node.key) < 0) {
            return getNode(node.left, k);
        } else {
            return getNode(node.right, k);
        }
    }


    @Override
    public V remove(K k) {
        Node node = getNode(root, k);
        if (node != null) {
            root = remove(root, k);
            return node.value;
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
        return getNode(root, k) != null;
    }

    @Override
    public V get(K k) {
        Node node = getNode(root, k);
        return node == null ? null : node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void set(K k, V v) {
        Node node = getNode(root, k);
        if (node == null) {
            throw new NoSuchElementException();
        }
        node.value = v;
    }

    private Node remove(Node node, K k) {
        if (node == null) {
            return null;
        }
        if (k.compareTo(node.key) < 0) {
            node.left = remove(node.left, k);
            return node;
        } else if (k.compareTo(node.key) > 0) {
            node.right = remove(node.right, k);
            return node;
        } else {
            // k == node.key

            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            Node successor = min(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node.left = null;
            node.right = null;
            return successor;
        }
    }

    public K min() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public K deleteMin() {
        K k = min();
        root = deleteMin(root);
        return k;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = deleteMin(node.left);
        return node;
    }
}

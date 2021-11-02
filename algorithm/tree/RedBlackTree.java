package algorithm.tree;

import java.util.NoSuchElementException;

/**
 * @author:qiming
 * @date: 2021/11/1
 */
public class RedBlackTree<K extends Comparable<K>, V> {

    private Node root;
    private int size;
    private final static boolean RED = true;
    private final static boolean BLACK = false;

    public RedBlackTree() {
        root = null;
        size = 0;
    }


    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        K key;
        V value;
        Node left, right;
        boolean color;

        Node(K k, V v) {
            this.key = k;
            this.value = v;
            left = null;
            right = null;
            color = RED;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color;
    }

    private Node leftRotate(Node node) {
        Node r = node.right;
        node.right = r.left;
        r.left = node;
        r.color = node.color;
        node.color = RED;
        return r;
    }

    private Node rightRotate(Node node) {
        Node l = node.left;
        node.left = l.right;
        l.right = node;
        l.color = node.color;
        node.color = RED;
        return l;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public void add(K k, V v) {
        root = add(root, k, v);
        root.color = BLACK;
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
        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    public boolean contains(K k) {
        return getNode(root, k) != null;
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

    public V get(K k) {
        Node node = getNode(root, k);
        return node == null ? null : node.value;
    }

    public void set(K k, V v) {
        Node node = getNode(root, k);
        if (node == null) {
            throw new NoSuchElementException();
        }
        node.value = v;
    }
}

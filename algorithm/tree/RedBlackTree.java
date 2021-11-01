package algorithm.tree;

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
        r.left = node;
        node.right = r.left;
        r.color = node.color;
        node.color = RED;
        return r;
    }
}

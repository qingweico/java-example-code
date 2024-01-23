package algorithm.tree;

import java.util.NoSuchElementException;

/**
 * 红黑树是一种自平衡的二叉搜索树
 * 红黑树的规则(五个)
 * 节点颜色规则->每个节点要么是红色, 要么是黑色
 * 根节点规则->根节点是黑色的
 * 叶子节点规则->每个叶子节点(NIL节点或空节点)是黑色的
 * 相邻节点规则->如果一个节点是红色的, 则它的两个子节点都是黑色的, 这保证了没有两个相邻的红色节点
 * 黑色深度规则->从任意节点到其每个叶子节点的路径上, 黑色节点的数量是相同的
 * 这条规则确保了从根到叶子的最长路径不会超过最短路径的两倍, 从而保持了树的平衡性
 * 这些性质可以保证使得红黑树在插入和删除节点时能够在对数时间内重新平衡, 保持了搜索和插入操作的高效性
 *
 * @author zqw
 * @date 2021/11/1
 */
public class RedBlackTree<K extends Comparable<K>, V> extends AbstractTree {

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

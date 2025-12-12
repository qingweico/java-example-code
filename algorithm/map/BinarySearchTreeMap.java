package algorithm.map;

import algorithm.tree.AbstractTree;

import java.util.NoSuchElementException;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class BinarySearchTreeMap<K extends Comparable<K>, V> extends AbstractTree<K, V> implements Map<K, V> {
    private Node root;
    private int size;

    public BinarySearchTreeMap() {
        root = null;
        size = 0;
    }

    /**
     * 二叉搜索树节点
     */
    private class Node extends BaseNode<Node, K, V> {
        Node(K k, V v) {
            super(k, v);
        }
    }

    @Override
    public void add(K k, V v) {
        root = addBst(root, k, v, createNodeHandler(), null);
    }

    /**
     * 创建节点处理器
     */
    private BstNodeHandler<Node, K, V> createNodeHandler() {
        return createStandardBstHandler((key, value) -> {
            size++;
            return new Node(key, value);
        });
    }

    private Node getNode(Node node, K k) {
        return getNode(node, k, n -> n.key, n -> n.left, n -> n.right);
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
        return removeBst(node, k, createNodeHandler(), null, n -> {
            size--;
            n.left = null;
            n.right = null;
        });
    }

    public K min() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        return min(root).key;
    }

    private Node min(Node node) {
        return minBst(node, createNodeHandler());
    }

    public K deleteMin() {
        K k = min();
        root = deleteMin(root);
        return k;
    }

    private Node deleteMin(Node node) {
        return deleteMinBst(node, createNodeHandler(), n -> {
            size--;
            n.right = null;
        });
    }
}

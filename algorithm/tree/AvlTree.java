package algorithm.tree;


import java.util.NoSuchElementException;

/**
 * 平衡二叉搜索树
 * 1. 首先是一颗二叉搜索树（BST）
 * 2. 平衡因子: 每个节点的左右子树高度差不超过 1
 * @author zqw
 * @date 2021/11/1
 */
public class AvlTree<K extends Comparable<K>, V> extends AbstractTree<K, V> {
    private Node root;
    private int size;

    public AvlTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private class Node extends BaseNode<Node, K, V> {
        int height;

        Node(K k, V v) {
            super(k, v);
            this.height = 1;
        }
    }
    // ------------------------------ Public ------------------------------

    public boolean isBalanced() {
        return isBalanced(root);
    }

    public void add(K k, V v) {
        root = addBst(root, k, v, createNodeHandler(), this::postProcessAfterInsert);
    }

    /**
     * AVL树插入后处理: 执行平衡调整
     */
    private Node postProcessAfterInsert(Node node) {
        return rebalance(node);
    }

    /**
     * 创建AVL树的节点
     */
    private BstNodeHandler<Node, K, V> createNodeHandler() {
        return createStandardBstHandler((key, value) -> {
            size++;
            return new Node(key, value);
        });
    }

    public K min() {
        if (size == 0) {
            throw new IllegalStateException("AvlTree size = 0");
        }
        return min(root).key;
    }

    public V remove(K k) {
        Node node = getNode(root, k);
        if (node != null) {
            root = remove(root, k);
            return node.value;
        } else {
            throw new NoSuchElementException();
        }

    }

    public boolean contains(K k) {
        return getNode(root, k) != null;
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

    public boolean isValidBst() {
        K min = min();
        return isValidBst(root, min);
    }

    /**
     * RR
     * +================================================================+
     * +                                                                +
     * +                                                                +
     * +          unbalance                          l                  +
     * +           /    \                          /  \                 +
     * +          l     x                         y   unbalance         +
     * +         / \             ======>         /\     /  \            +
     * +        y  node                         m  n   node x           +
     * +       /\                                                       +
     * +      m  n                                                      +
     * +================================================================+
     * LR
     * +================================================================+
     * +                                                                +
     * +                                                                +
     * +     unbalance             unbalance              node          +
     * +      /    \                 /    \              /   \          +
     * +     l     x      LL       node    x   RR       l   unbalance   +
     * +    / \        ======>     / \       =====>    / \     /  \     +
     * +   y   node               l   n               y  m    n   x     +
     * +       / \               / \                                    +
     * +      m  n              y   m                                   +
     * +================================================================+
     *
     * @param unbalance unbalance node
     * @return new balance node
     * @see AvlTree#leftRotate(Node)
     */
    private Node rightRotate(Node unbalance) {
        Node l = unbalance.left;
        Node node = l.right;
        l.right = unbalance;
        unbalance.left = node;

        unbalance.height = Math.max(getHeight(unbalance.left), getHeight(unbalance.right)) + 1;
        l.height = Math.max(getHeight(l.left), getHeight(l.right)) + 1;
        return l;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * LL
     * +================================================================+
     * +                                                                +
     * +                                                                +
     * +           unbalance                         r                  +
     * +            /    \                         /  \                 +
     * +           x      r        =====>   unbalance  y                +
     * +                 / \                  /  \    / \               +
     * +               node y                x  node  m  n              +
     * +                   / \                                          +
     * +                  m   n                                         +
     * +================================================================+
     * <p>
     * RL
     * +================================================================+
     * +                                                                +
     * +                                                                +
     * +     unbalance             unbalance                 node       +
     * +      /    \                 /    \                /     \      +
     * +     x     r       RR      x    node      LL     unbalance  r   +
     * +          / \    ======>           / \  ======>     / \    / \  +
     * +        node y                    m   r           x  m   n  y   +
     * +        / \                          / \                        +
     * +       m  n                          n  y                       +
     * +================================================================+
     *
     * @param unbalance unbalance node
     * @return new balance node
     */
    private Node leftRotate(Node unbalance) {
        Node r = unbalance.right;
        Node node = r.left;
        r.left = unbalance;
        unbalance.right = node;
        unbalance.height = Math.max(getHeight(unbalance.left), getHeight(unbalance.right)) + 1;
        r.height = Math.max(getHeight(r.left), getHeight(r.right)) + 1;
        return r;
    }

    private boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }
        int balancedFactor = getBalanceFactor(node);
        if (Math.abs(balancedFactor) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private void setHeight(Node node) {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }


    private Node getNode(Node node, K k) {
        return getNode(node, k, n -> n.key, n -> n.left, n -> n.right);
    }

    // ------------------------------ Private ------------------------------

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    private Node remove(Node node, K k) {
        if (node == null) {
            return null;
        }
        Node cur;
        if (k.compareTo(node.key) < 0) {
            node.left = remove(node.left, k);
            cur = node;
        } else if (k.compareTo(node.key) > 0) {
            node.right = remove(node.right, k);
            cur = node;
        } else {
            // k.compareTo(node.key) == 0

            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                cur = rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                cur = leftNode;
            } else {
                Node successor = min(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = null;
                node.right = null;
                cur = successor;
            }
        }
        if (cur == null) {
            return null;
        }
        return rebalance(cur);
    }

    private boolean isValidBst(Node node, K min) {
        if (node == null) {
            return true;
        }
        if (isValidBst(node.left, min)) {
            if (min.compareTo(node.key) <= 0) {
                min = node.key;
                return isValidBst(node.right, min);
            }
        }
        return false;
    }

    private Node rebalance(Node node) {
        setHeight(node);
        int balanceFactor = getBalanceFactor(node);
        // RR
        if (balanceFactor > 1 && getBalanceFactor((node.left)) >= 0) {
            return rightRotate(node);
        }
        // LL
        if (balanceFactor < -1 && getBalanceFactor((node.right)) <= 0) {
            return leftRotate(node);
        }
        // LR
        if (balanceFactor > 1 && getBalanceFactor((node.left)) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        // RL
        if (balanceFactor < -1 && getBalanceFactor((node.right)) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }
}

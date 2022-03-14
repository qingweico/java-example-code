package algorithm.tree;

import java.util.NoSuchElementException;

/**
 * @author zqw
 * @date 2021/11/1
 */
public class AVLTree<K extends Comparable<K>, V> {
    private Node root;
    private int size;

    public AVLTree() {
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
        int height;

        Node(K k, V v) {
            this.key = k;
            this.value = v;
            left = null;
            right = null;
            height = 1;
        }
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
     * @see AVLTree#leftRotate(Node)
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

    public boolean isBalanced() {
        return isBalanced(root);
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
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
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

    public K min() {
        if (size == 0) {
            throw new IllegalStateException("AVLTree size = 0");
        }
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
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
            // e.compareTo(node.e) == 0

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
        cur.height = 1 + Math.max(getHeight(cur.left), getHeight(cur.right));
        int balanceFactor = getBalanceFactor(cur);
        // RR
        if (balanceFactor > 1 && getBalanceFactor((cur.left)) >= 0) {
            return rightRotate(cur);
        }
        // LL
        if (balanceFactor < -1 && getBalanceFactor((cur.right)) <= 0) {
            return leftRotate(cur);
        }
        // LR
        if (balanceFactor > 1 && getBalanceFactor((cur.left)) < 0) {
            node.left = leftRotate(cur.left);
            return rightRotate(cur);
        }
        // RL
        if (balanceFactor < 1 && getBalanceFactor((cur.right)) > 0) {
            node.right = leftRotate(cur.right);
            return leftRotate(cur);
        }
        return cur;
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

    public boolean isValidBST() {
        K min = min();
        return isValidBST(root, min);
    }

    private boolean isValidBST(Node node, K min) {
        if (node == null) {
            return true;
        }
        if (isValidBST(node.left, min)) {
            if (min.compareTo(node.key) <= 0) {
                min = node.key;
                return isValidBST(node.right, min);
            }
        }
        return false;
    }
}

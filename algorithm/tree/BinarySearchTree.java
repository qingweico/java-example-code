package algorithm.tree;

import java.util.LinkedList;
import java.util.Stack;

import static util.Print.*;

/**
 * 二叉搜索树
 *
 * @author zqw
 * @date 2021/10/28
 */
public class BinarySearchTree<E extends Comparable<E>> {

    class Node {
        E e;
        Node left;
        Node right;

        public Node(E e) {
            this.e = e;
            this.left = null;
            this.right = null;
        }
    }

    private int size;
    private Node root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(E e) {
        root = add(root, e);
    }

    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            node.right = add(node.right, e);
        }
        return node;
    }

    public void put(E e) {
        Node node = new Node(e);
        if (root == null) {
            root = node;
            size++;
            return;
        }
        put(root, e);
    }

    private void put(Node node, E e) {

        if (e.compareTo(node.e) < 0) {
            if (node.left == null) {
                node.left = new Node(e);
                size++;
            }
            put(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            if (node.right == null) {
                node.right = new Node(e);
                size++;
            }
            put(node.right, e);
        }
        // ignore e.compareTo(node.e) == 0
    }

    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.e) == 0) {
            return true;
        } else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /**深度优先搜索(Depth-First Search)*/
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        print(node.e + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    public void preOrderNr() {
        preOrderNr(root);
    }

    private void preOrderNr(Node node) {
        Stack<Node> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            prints(cur.e);
            if (cur.right != null) {
                stack.push(cur.right);
            }
            if (cur.left != null) {
                stack.push(cur.left);
            }
        }
    }

    public void levelOrder() {
        levelOrder(root);
    }

    /**广度优先搜索(Breadth-First Search)*/
    private void levelOrder(Node node) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            prints(cur.e);
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    public void reverse() {
        reverse(root);
    }

    private void reverse(Node node) {
        if (node == null) {
            return;
        }
        Node n = node.left;
        node.left = node.right;
        node.right = n;
        reverse(node.left);
        reverse(node.right);
    }

    public int depth() {
        return depth(root);
    }

    private int depth(Node node) {
        return node == null ? 0 : Math.max(depth(node.left), depth(node.right)) + 1;
    }

    public E min() {
        if (size == 0) {
            throw new IllegalStateException("tree size = 0");
        }
        return min(root).e;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        }
        return min(node.left);
    }

    public E max() {
        if (size == 0) {
            throw new IllegalStateException("tree size = 0");
        }
        return max(root).e;
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    public E deleteMin() {
        E e = min();
        root = deleteMin(root);
        return e;
    }

    /**
     * @param node 删除掉以 node 为根的二叉搜索树中最小的节点
     * @return 返回删除节点后新的二叉搜索树的根
     * @see BinarySearchTree#deleteMax(Node)
     */
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

    public E deleteMax() {
        E e = max();
        root = deleteMax(root);
        return e;
    }

    /**
     * @param node 删除掉以 node 为根的二叉搜索树中最大的节点
     * @return 返回删除节点后新的二叉搜索树的根
     * @see BinarySearchTree#deleteMin(Node)
     */
    private Node deleteMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = deleteMax(node.right);
        return node;
    }

    public void remove(E e) {
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        }
        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else {
            // e.compareTo(node.e) == 0

            //     x
            //    / \
            //  null  y
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //     x
            //    / \
            //   y  null
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //              node                successor
            //              / \                  /     \
            //             x   y      ==>       x       y
            //                / \                      / \
            //        successor   o                  null o
            Node successor = min(node.right);
            successor.right = deleteMin(node.right);
            successor.left = node.left;
            node.left = null;
            node.right = null;
            return successor;
        }
    }

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node root) {
        int h = depth(root);
        int interval = 2;
        int w = 2 * (int) Math.pow(2, h);
        var lines = new StringBuilder[h * 2];
        for (int i = 0; i < h * interval; i++) {
            lines[i] = new StringBuilder(String.format("%" + w + "s", ""));
        }

        printNode(lines, w, root, 0, 0);
        for (var line : lines) {
            println(line.toString());
        }

    }

    private void printNode(StringBuilder[] lines, int w, Node node, int h, int base) {
        var number = Math.pow(2, h);
        var pos = base + (int) (w / (number * 2));

        var str = node.e.toString();
        for (int i = 0; i < str.length(); i++) {
            lines[h * 2].setCharAt(pos + i, str.charAt(i));
        }

        if (node.left != null) {
            lines[h * 2 + 1].setCharAt(pos - 1, '/');
            printNode(lines, w, node.left, h + 1, base);
        }

        if (node.right != null) {
            lines[h * 2 + 1].setCharAt(pos + str.length() + 1, '\\');
            printNode(lines, w, node.right, h + 1, pos);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        generateBstString(root, 0, sb);
        return sb.toString();
    }

    private void generateBstString(Node node, int depth, StringBuilder sb) {
        if (node == null) {
            sb.append(generateDepthString(depth)).append("null\n");
            return;
        }
        sb.append(generateDepthString(depth)).append(node.e).append("\n");
        generateBstString(node.left, depth + 1, sb);
        generateBstString(node.right, depth + 1, sb);
    }

    private String generateDepthString(int depth) {
        return "--".repeat(Math.max(0, depth));
    }
}


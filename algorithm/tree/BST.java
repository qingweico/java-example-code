package algorithm.tree;

import algorithm.sort.Tools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import static util.Print.print;
import static util.Print.printnb;

/**
 * @author:qiming
 * @date: 2021/10/28
 */
public class BST<E extends Comparable<E>> {

    private class Node {
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
            return;
        }
        put(root, e);
    }

    private void put(Node node, E e) {

        if (e.compareTo(node.e) < 0) {
            if (node.left == null) {
                node.left = new Node(e);
            }
            put(node.left, e);
        } else if (e.compareTo(node.e) > 0) {
            if (node.right == null) {
                node.right = new Node(e);
            }
            put(node.right, e);
        }
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

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        printnb(node.e + " ");
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
            printnb(cur.e + " ");
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

    private void levelOrder(Node node) {
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            printnb(cur.e + " ");
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
            throw new IllegalStateException();
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
            throw new IllegalStateException();
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
            // e == node.e

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

    public void printTree() {
        printTree(root);
    }

    private void printTree(Node root) {
        int h = depth(root);
        int W = 2 * (int) Math.pow(2, h);
        var lines = new StringBuilder[h * 2];
        for (int i = 0; i < h * 2; i++) {
            lines[i] = new StringBuilder(String.format("%" + W + "s", ""));
        }

        printNode(lines, W, root, 0, 0);
        for (var line : lines) {
            print(line.toString());
        }

    }

    private void printNode(StringBuilder[] lines, int W, Node node, int h, int base) {
        var nums = Math.pow(2, h);
        var pos = base + (int) (W / (nums * 2));

        var str = node.e.toString();
        for (int i = 0; i < str.length(); i++) {
            lines[h * 2].setCharAt(pos + i, str.charAt(i));
        }

        if (node.left != null) {
            lines[h * 2 + 1].setCharAt(pos - 1, '/');
            printNode(lines, W, node.left, h + 1, base);
        }

        if (node.right != null) {
            lines[h * 2 + 1].setCharAt(pos + str.length() + 1, '\\');
            printNode(lines, W, node.right, h + 1, pos);
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

    @Test
    public void testBst() {
        BST<Integer> bst = new BST<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            bst.add((int) (Math.random() * 1000));
        }
        print(bst.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!bst.isEmpty()) {
            list.add(bst.deleteMax());
        }
        Tools.assertSorted(list, true);
        print(list);
    }

    @Test
    public void testPrint() {
        int[] nums = {5, 6, 1, 8, 3, 0, 9};
        BST<Integer> bst = new BST<>();
        for (var i : nums) {
            bst.add(i);
        }
        bst.printTree();
        bst.reverse();
        bst.printTree();
        bst.levelOrder();
        print();
        bst.preOrderNr();
    }
}

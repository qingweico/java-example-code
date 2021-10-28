package algorithm.tree;

import algorithm.sort.Tools;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

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
            return contains(node, e);
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.print(node.e + " ");
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
            System.out.print(cur.e + " ");
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
            System.out.print(cur.e + " ");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
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
    public void print() {
        int[] nums = {5, 6, 1, 8, 3, 0, 9};
        BST<Integer> bst = new BST<>();
        for (var i : nums) {
            bst.add(i);
        }
        bst.levelOrder();
    }

    @Test
    public void testBst() {
        BST<Integer> bst = new BST<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            bst.add((int) (Math.random() * 1000));
        }
        System.out.println(bst.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!bst.isEmpty()) {
            list.add(bst.deleteMax());
        }
        Tools.assertSorted(list, true);
        System.out.println(list);
    }

    @Test
    public void nam() {
        int depth = 4;
        System.out.printf("%" + depth + "s", "");
    }
}

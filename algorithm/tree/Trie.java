package algorithm.tree;

import java.util.TreeMap;

/**
 * @author:qiming
 * @date: 2021/11/14
 */
public class Trie {
    class Node {
        boolean ending;
        TreeMap<Character, Node> next;

        public Node(boolean ending) {
            this.ending = ending;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private final Node root;
    private int size;

    public int size() {
        return size;
    }

    public Trie() {
        root = new Node();
        size = 0;
    }

    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.ending) {
            cur.ending = true;
            size++;
        }
    }

    public boolean contains(String word, boolean prefix) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                return false;
            }
            cur = cur.next.get(c);
        }
        return prefix || cur.ending;
    }
}

package thinking.genericity;

/**
 * A stack implemented with an internal linked structure.
 *
 * @author zqw
 * @date 2021/3/23
 */
public class LinkedStack<T> {

    private class Node {
        Node next;
        T item;

        Node() {
            item = null;
            next = null;
        }

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    // End Sentinel
    // head insert
    private Node top = new Node();

    public void push(T item) {
        top = new Node(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> ls = new LinkedStack<>();
        for (String s : "we don't talk any more".split(" ")) {
            ls.push(s);
        }
        String s;
        while ((s = ls.pop()) != null) {
            System.out.println(s);
        }
    }
}

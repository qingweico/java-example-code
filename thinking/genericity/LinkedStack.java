package thinking.genericity;

/**
 * @author:qiming
 * @date: 2021/3/23
 */
public class LinkedStack<T> {
    private static class Node<U> {
        U item;
        Node<U> next;
        Node() {
            item = null;
            next = null;
        }
        Node(U item, Node<U> next) {
            this.item = item;
            this.next = next;
        }
        boolean end() {
            return item == null && next == null;
        }
    }
    // End Sentinel
    private Node<T> top = new Node<>();
    public void push(T item) {
        top = new Node<>(item, top);
    }
    public T pop() {
        T result = top.item;
        if(!top.end()) {
            top = top.next;
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedStack<String> ls = new LinkedStack<>();
        for(String s : "we don't talk any more".split(" ")) {
            ls.push(s);
        }
        String s;
        while((s = ls.pop()) != null) {
            System.out.println(s);
        }
    }
}

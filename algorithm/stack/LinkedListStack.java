package algorithm.stack;

import algorithm.list.LinkedList;

/**
 * @author zqw
 * @date 2021/10/31
 */
public class LinkedListStack<E> implements Stack<E> {
    private final LinkedList<E> linkedList;

    public LinkedListStack() {
        linkedList = new LinkedList<>();
    }

    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public String toString() {
        return "LinkedListStack: top->" +
                linkedList;
    }
}

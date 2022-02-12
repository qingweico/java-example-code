package algorithm.stack;

import algorithm.array.Array;

/**
 * @author zqw
 * @date 2021/10/30
 */
public class ArrayStack<E> implements Stack<E> {
    private final Array<E> array;

    public ArrayStack(int capacity) {
        array = new Array<>(capacity);
    }

    public ArrayStack() {
        array = new Array<>();
    }

    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("ArrayStack: ");
        res.append('[');
        for (int i = 0; i < size(); i++) {
            res.append(array.get(i));
            if (i != size() - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }
}

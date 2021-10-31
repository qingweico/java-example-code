package algorithm.queue;

import algorithm.array.Array;

/**
 * @author:qiming
 * @date: 2021/10/30
 */
public class ArrayQueue<E> implements Queue<E> {
    private final Array<E> array;

    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    public ArrayQueue() {
        array = new Array<>();
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
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E front() {
        return array.getFirst();
    }

    public int capacity() {
        return array.capacity();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array Queue: size = %d, capacity = %d\n", size(), capacity()));
        res.append("front [");
        for (int i = 0; i < size(); i++) {
            res.append(array.get(i));
            if (i != size() - 1) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }
}

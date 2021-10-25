package algorithm;

import java.util.ArrayList;

/**
 * @author:qiming
 * @date: 2021/10/25
 */
public class Array {
    private final Object[] data;
    private int size;
    private final int capacity;
    private final static int DEFAULT_CAPACITY = 10;

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    public Array(int capacity) {
        data = new Object[capacity];
        this.capacity = capacity;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add(Object e, int index) {
        if (size == capacity || index < 0 || index > size) {
            throw new IllegalArgumentException();
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
        return true;
    }

    public boolean addLast(Object e) {
        return add(e, size);
    }

    public boolean addFirst(Object e) {
        return add(e, 0);
    }
}

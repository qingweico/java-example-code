package algorithm.array;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/25
 */
public class Array<E> {
    private E[] data;
    private int size;
    private int capacity;
    private final static int DEFAULT_CAPACITY = 10;

    public Array() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        this.capacity = capacity;
    }

    @SuppressWarnings("unchecked")
    public Array(E[] A) {
        data = (E[]) new Object[A.length];
        System.arraycopy(A, 0, data, 0, A.length);
        size = A.length;
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

    public void add(E e, int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index = " + index);
        }
        ensureCapacity();
        if (size - index >= 0) {
            System.arraycopy(data, index, data, index + 1, size - index);
        }
        data[index] = e;
        size++;
    }

    private void ensureCapacity() {
        if (size >= capacity()) {
            resize(capacity << 1);
        }
    }

    private void resize(int newCapacity) {
        data = Arrays.copyOf(data, newCapacity);
        capacity = newCapacity;
    }

    public boolean addLast(E e) {
        add(e, size());
        return true;
    }

    public boolean addFirst(E e) {
        add(e, 0);
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index = " + index);
        }
        return data[index];
    }

    public E getLast() {
        return get(size() - 1);
    }

    public E getFirst() {
        return get(0);
    }

    public Object set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index = " + index);
        }
        Object oldValue = data[index];
        data[index] = e;
        return oldValue;
    }

    public boolean contains(Object e) {
        for (int i = 0; i < size; i++) {
            if (e.equals(data[i])) {
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index = " + index);
        }
        E oldValue = data[index];
        if (size - (index + 1) >= 0) {
            System.arraycopy(data, index + 1, data, index + 1 - 1, size - (index + 1));
        }

        size--;
        if (size <= capacity() >> 2 && capacity() >> 1 != 0) {
            resize(capacity >> 1);
        }
        // loiter object != memory leak
        data[size] = null;
        return oldValue;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public void swap(int l, int r) {
        if (l < 0 || r < 0 || l >= size || r >= size) {
            throw new IllegalArgumentException();
        }
        E e = data[l];
        data[l] = data[r];
        data[r] = e;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, capacity));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }
}

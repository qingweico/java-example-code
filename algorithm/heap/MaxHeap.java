package algorithm.heap;

import algorithm.array.Array;
import algorithm.sort.Tools;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author:qiming
 * @date: 2021/10/29
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap() {
        this.data = new Array<>();
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index = " + index);
        }
        return (index - 1) >> 1;
    }

    public int leftChild(int index) {
        return index * 2 + 1;
    }

    public int rightChild(int index) {
        return index * 2 + 2;
    }

    public void add(E e) {
        data.addLast(e);
        siftUp(size() - 1);
    }

    private void siftUp(int k) {
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    public E top() {
        if (size() == 0) {
            throw new IllegalArgumentException();
        }
        return data.get(0);
    }

    public E extractMax() {
        E e = top();
        data.swap(0, size() - 1);
        data.removeLast();
        siftDown(0);
        return e;
    }

    private void siftDown(int k) {
        while (leftChild(k) < size()) {
            int max = leftChild(k);
            // find the largest of the left and right child nodes.
            if (rightChild(k) < size() && data.get(rightChild(k)).
                    compareTo(data.get(max)) > 0) {
                max = rightChild(k);
            }
            if (data.get(k).compareTo(data.get(max)) > 0) {
                break;
            }
            data.swap(k, max);
            k = max;
        }
    }

    public void heapify(E[] A) {
        data = new Array<>(A);
        for (int i = parent(A.length - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    @Test
    public void testHeap() {
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        int count = 100_000;
        for (int i = 0; i < count; i++) {
            maxHeap.add((int) (Math.random() * 1000000));
        }
        System.out.println(maxHeap.size());
        ArrayList<Integer> list = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            list.add(maxHeap.extractMax());
        }
        Tools.assertSorted(list, true);
        System.out.println(list);
    }
}

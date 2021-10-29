package algorithm.sort;

import algorithm.heap.MaxHeap;

/**
 * @author:qiming
 * @date: 2021/10/29
 */
public class HeapSort<E extends Comparable<E>> {
    public void sort(E[] A) {
        MaxHeap<E> maxHeap = new MaxHeap<>();
        // nlogn
        for (var i : A) {
            maxHeap.add(i);
        }
        for (int i = A.length - 1; i >= 0; i--) {
            A[i] = maxHeap.extractMax();
        }
    }
}

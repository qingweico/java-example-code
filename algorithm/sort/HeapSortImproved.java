package algorithm.sort;

import algorithm.heap.MaxHeap;

/**
 * @author:qiming
 * @date: 2021/10/29
 */
public class HeapSortImproved<E extends Comparable<E>> {
    public void sort(E[] A) {
        MaxHeap<E> maxHeap = new MaxHeap<>();
        // n
        maxHeap.heapify(A);
        for (int i = A.length - 1; i >= 0; i--) {
            A[i] = maxHeap.extractMax();
        }
    }
}

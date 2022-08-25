package algorithm.sort;

import algorithm.heap.MaxHeap;

/**
 * @author zqw
 * @date 2021/10/29
 */
public class HeapSortImproved<E extends Comparable<E>> {
    public void sort(E[] e) {
        MaxHeap<E> maxHeap = new MaxHeap<>();
        // n
        maxHeap.heapify(e);
        for (int i = e.length - 1; i >= 0; i--) {
            e[i] = maxHeap.extractMax();
        }
    }
}

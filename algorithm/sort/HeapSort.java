package algorithm.sort;

import algorithm.heap.MaxHeap;

/**
 * 堆排序
 * @author zqw
 * @date 2021/10/29
 */
public class HeapSort<E extends Comparable<E>> {
    public void sort(E[] e) {
        MaxHeap<E> maxHeap = new MaxHeap<>();
        // n * logn
        for (var i : e) {
            maxHeap.add(i);
        }
        for (int i = e.length - 1; i >= 0; i--) {
            e[i] = maxHeap.extractMax();
        }
    }
}

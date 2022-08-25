package algorithm.sort;

import util.Tools;

/**
 * @author zqw
 * @date 2021/10/29
 */
public class InPlaceHeapSort<E extends Comparable<E>> {
    public void sort(E[] e) {
        if (e.length <= 1) {
            return;
        }
        for (int i = (e.length - 2) >> 1; i >= 0; i--) {
            siftDown(e, i, e.length);
        }
        for (int i = e.length - 1; i >= 0; i--) {
            Tools.swap(e, 0, i);
            siftDown(e, 0, i);
        }
    }

    private void siftDown(E[] e, int k, int n) {

        while (2 * k + 1 < n) {
            // left
            int left = k * 2 + 1;
            // right
            int right = k * 2 + 2;
            if (right < n && e[right].compareTo(e[left]) > 0) {
                left = right;
            }
            if (e[k].compareTo(e[left]) >= 0) {
                break;
            }
            Tools.swap(e, k, left);
            k = left;
        }
    }
}

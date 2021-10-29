package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/29
 */
public class InPlaceHeapSort<E extends Comparable<E>> {
    public void sort(E[] A) {
        if (A.length <= 1) {
            return;
        }
        for (int i = (A.length - 2) >> 1; i >= 0; i--) {
            siftDown(A, i, A.length);
        }
        for (int i = A.length - 1; i >= 0; i--) {
            Tools.swap(A, 0, i);
            siftDown(A, 0, i);
        }
    }

    private void siftDown(E[] A, int k, int n) {

        while (2 * k + 1 < n) {
            // left
            int left = k * 2 + 1;
            // right
            int right = k * 2 + 2;
            if (right < n && A[right].compareTo(A[left]) > 0) {
                left = right;
            }
            if (A[k].compareTo(A[left]) >= 0) {
                break;
            }
            Tools.swap(A, k, left);
            k = left;
        }
    }
}

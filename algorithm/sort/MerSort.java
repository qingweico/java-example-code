package algorithm.sort;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/26
 */
public class MerSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        // []
        mergeSort(A, 0, A.length - 1);
    }

    private <E extends Comparable<E>> void mergeSort(E[] A, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(A, l, mid);
        mergeSort(A, mid + 1, r);
        if (A[mid].compareTo(A[mid + 1]) > 0) {
            merge(A, l, mid, r);
        }

    }

    private <E extends Comparable<E>> void merge(E[] A, int l, int mid, int r) {
        E[] B = Arrays.copyOfRange(A, l, r + 1);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                A[k] = B[j - l];
                j++;
            } else if (j > r) {
                A[k] = B[i - l];
                i++;
            } else if (B[i - l].compareTo(B[j - l]) > 0) {
                A[k] = B[j - l];
                j++;
            } else {
                A[k] = B[i - l];
                i++;
            }
        }
    }
}

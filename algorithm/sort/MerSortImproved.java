package algorithm.sort;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/26
 */
public class MerSortImproved {
    public <E extends Comparable<E>> void sort(E[] A) {
        E[] shared = Arrays.copyOf(A, A.length);
        // []
        mergeSort(A, 0, A.length - 1, shared);
    }

    private <E extends Comparable<E>> void mergeSort(E[] A, int l, int r, E[] shared) {
        if (l >= r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(A, l, mid, shared);
        mergeSort(A, mid + 1, r, shared);
        if (A[mid].compareTo(A[mid + 1]) > 0) {
            merge(A, l, mid, r, shared);
        }

    }

    public static <E extends Comparable<E>> void merge(E[] A, int l, int mid, int r, E[] shared) {
        System.arraycopy(A, l, shared, l, r - l + 1);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                A[k] = shared[j++];
            } else if (j > r) {
                A[k] = shared[i++];
            } else if (shared[i].compareTo(shared[j]) > 0) {
                A[k] = shared[j++];
            } else {
                A[k] = shared[i++];
            }
        }
    }
}

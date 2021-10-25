package algorithm.sort;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class MergeSort implements MutableSorter {
    @Override
    public void sort(int[] A) {
        mergeSort(A, 0, A.length);
    }

    private void mergeSort(int[] A, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(A, l, mid);
        mergeSort(A, mid, r);
        merge(A, l, mid + 1, r);
    }

    private void merge(int[] A, int l, int mid, int r) {
        int[] B = Arrays.copyOfRange(A, l, mid + 1);
        int[] C = Arrays.copyOfRange(A, mid, r + 1);
        B[B.length - 1] = C[C.length - 1] = Integer.MAX_VALUE;

        int b = 0, c = 0;
        for (int k = l; k < r; k++) {
            if (B[b] > C[c]) {
                A[k] = C[c++];
            } else {
                A[k] = B[b++];
            }
        }
    }
}



package algorithm.sort;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/26
 */
public class MerSortBottomUp {
    public <E extends Comparable<E>> void sort(E[] A) {
        var len = A.length;
        var shared = Arrays.copyOf(A, len);
        mergeSort(A, A.length, shared);

    }
    public static <E extends Comparable<E>> void mergeSort(E[] A, int len, E[] shared) {
        for (int step = 1; step < len; step += step) {
            for (int i = 0; i + step < len; i += step * 2) {
                if (A[i + step - 1].compareTo(A[i + step]) > 0) {
                    MerSortImproved.merge(A, i, i + step - 1, Math.min(i + step * 2 - 1, len - 1), shared);
                }
            }
        }
    }
}

package algorithm.sort;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/10/26
 */
public class MerSortBottomUpImproved {
    public <E extends Comparable<E>> void sort(E[] A) {
        var len = A.length;
        var shared = Arrays.copyOf(A, len);

        for (int k = 0; k < len; k += 16) {
            InsSortImproved.sort(A, k, Math.min(k + 15, len - 1) + 1);
        }
        MerSortBottomUp.mergeSort(A, A.length, shared);
    }
}

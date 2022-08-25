package algorithm.sort;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/10/26
 */
public class MerSortBottomUpImproved {
    public <E extends Comparable<E>> void sort(E[] e) {
        var len = e.length;
        var shared = Arrays.copyOf(e, len);

        for (int k = 0; k < len; k += 16) {
            InsSortImproved.sort(e, k, Math.min(k + 15, len - 1) + 1);
        }
        MerSortBottomUp.mergeSort(e, e.length, shared);
    }
}

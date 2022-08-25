package algorithm.sort;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/10/26
 */
public class MerSortBottomUp {
    public <E extends Comparable<E>> void sort(E[] e) {
        var len = e.length;
        var shared = Arrays.copyOf(e, len);
        mergeSort(e, e.length, shared);

    }
    public static <E extends Comparable<E>> void mergeSort(E[] e, int len, E[] shared) {
        for (int step = 1; step < len; step += step) {
            for (int i = 0; i + step < len; i += step * 2) {
                if (e[i + step - 1].compareTo(e[i + step]) > 0) {
                    MerSortImproved.merge(e, i, i + step - 1, Math.min(i + step * 2 - 1, len - 1), shared);
                }
            }
        }
    }
}

package algorithm.sort;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/10/26
 */
public class MerSortImproved {
    public <E extends Comparable<E>> void sort(E[] e) {
        E[] shared = Arrays.copyOf(e, e.length);
        // []
        mergeSort(e, 0, e.length - 1, shared);
    }

    private <E extends Comparable<E>> void mergeSort(E[] e, int l, int r, E[] shared) {
        if (l >= r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(e, l, mid, shared);
        mergeSort(e, mid + 1, r, shared);
        if (e[mid].compareTo(e[mid + 1]) > 0) {
            merge(e, l, mid, r, shared);
        }

    }

    public static <E extends Comparable<E>> void merge(E[] e, int l, int mid, int r, E[] shared) {
        System.arraycopy(e, l, shared, l, r - l + 1);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                e[k] = shared[j++];
            } else if (j > r) {
                e[k] = shared[i++];
            } else if (shared[i].compareTo(shared[j]) > 0) {
                e[k] = shared[j++];
            } else {
                e[k] = shared[i++];
            }
        }
    }
}

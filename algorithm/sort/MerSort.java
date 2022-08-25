package algorithm.sort;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/10/26
 */
public class MerSort {
    public <E extends Comparable<E>> void sort(E[] e) {
        // []
        mergeSort(e, 0, e.length - 1);
    }

    private <E extends Comparable<E>> void mergeSort(E[] e, int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(e, l, mid);
        mergeSort(e, mid + 1, r);
        if (e[mid].compareTo(e[mid + 1]) > 0) {
            merge(e, l, mid, r);
        }

    }

    private <E extends Comparable<E>> void merge(E[] e, int l, int mid, int r) {
        E[] bArray = Arrays.copyOfRange(e, l, r + 1);
        int i = l;
        int j = mid + 1;
        for (int k = l; k <= r; k++) {
            if (i > mid) {
                e[k] = bArray[j - l];
                j++;
            } else if (j > r) {
                e[k] = bArray[i - l];
                i++;
            } else if (bArray[i - l].compareTo(bArray[j - l]) > 0) {
                e[k] = bArray[j - l];
                j++;
            } else {
                e[k] = bArray[i - l];
                i++;
            }
        }
    }
}

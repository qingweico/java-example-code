package algorithm.sort;

import java.util.Arrays;

/**
 * @author zqw
 * @date 2021/10/16
 */
public class MergeSort implements MutableSorter {
    @Override
    public void sort(int[] e) {
        // [)
        mergeSort(e, 0, e.length);
    }

    private void mergeSort(int[] e, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(e, l, mid);
        mergeSort(e, mid, r);
        if (e[mid - 1] > e[mid]) {
            merge(e, l, mid, r);

        }

    }

    private void merge(int[] e, int l, int mid, int r) {
        // [)
        int[] bArray = Arrays.copyOfRange(e, l, mid + 1);
        int[] cArray = Arrays.copyOfRange(e, mid, r + 1);
        bArray[bArray.length - 1] = cArray[cArray.length - 1] = Integer.MAX_VALUE;

        int b = 0, c = 0;
        for (int k = l; k < r; k++) {
            if (bArray[b] > cArray[c]) {
                e[k] = cArray[c++];
            } else {
                e[k] = bArray[b++];
            }
        }
    }
}



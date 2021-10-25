package algorithm.sort;

import java.nio.channels.Selector;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class SelectionSort implements MutableSorter{

    public void sort(int[] A) {
        for (int i = A.length - 1; i >= 0; i--) {
            int j = maxIndex(A, i + 1);
            Tools.swap(A, i, j);
        }
    }

    private static int maxIndex(int[] A, int r) {
        int max = Integer.MIN_VALUE;
        // stable
        int maxIndex = r - 1;
        for (int k = maxIndex; k >= 0; k--) {
            if (max < A[k]) {
                max = A[k];
                maxIndex = k;
            }
        }
        return maxIndex;
    }

}

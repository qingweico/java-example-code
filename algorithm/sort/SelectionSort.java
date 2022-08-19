package algorithm.sort;

import util.Tools;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class SelectionSort implements MutableSorter {

    @Override
    public void sort(int[] e) {
        for (int i = e.length - 1; i >= 0; i--) {
            int j = maxIndex(e, i + 1);
            Tools.swap(e, i, j);
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

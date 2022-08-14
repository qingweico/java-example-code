package algorithm.sort;

import util.Tools;

/**
 * @author:qiming
 * @date: 2021/10/25
 */
public class SelSort {

    public <E extends Comparable<E>> void sort(E[] A) {
        for (int i = 0; i < A.length; i++) {
            int min = i;
            for (int j = i + 1; j < A.length; j++) {
                if (A[j].compareTo(A[min]) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                Tools.swap(A, i, min);
            }
        }
    }
}

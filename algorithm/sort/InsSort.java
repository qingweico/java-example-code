package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/25
 */
public class InsSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        for (int i = 1; i < A.length; i++) {
            for (int j = i; j > 0 && A[j].compareTo(A[j - 1]) < 0; j--) {
                Tools.swap(A, j, j - 1);
            }
        }
    }
    public <E extends Comparable<E>> void backSort(E[] A) {
        for (int i = A.length - 2; i >= 0; i--) {
            for (int j = i; j < A.length - 1 && A[j].compareTo(A[j + 1]) > 0; j++) {
                Tools.swap(A, j, j + 1);
            }
        }
    }
}

package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/25
 */
public class InsSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        sort(A, 0, A.length);
    }

    public <E extends Comparable<E>> void backSort(E[] A) {
        for (int i = A.length - 2; i >= 0; i--) {
            for (int j = i; j < A.length - 1 && A[j].compareTo(A[j + 1]) > 0; j++) {
                Tools.swap(A, j, j + 1);
            }
        }
    }

    public static <E extends Comparable<E>> void sort(E[] A, int l, int r) {
        for (int i = 1; i < r; i++) {
            for (int j = i; j > l && A[j].compareTo(A[j - 1]) < 0; j--) {
                Tools.swap(A, j, j - 1);
            }
        }
    }
}

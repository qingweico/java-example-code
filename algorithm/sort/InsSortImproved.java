package algorithm.sort;


/**
 * @author:qiming
 * @date: 2021/10/26
 */
public class InsSortImproved {
    public <E extends Comparable<E>> void sort(E[] A) {
        sort(A, 0, A.length);
    }

    public static <E extends Comparable<E>> void sort(E[] A, int l, int r) {
        for (int i = l; i < r; i++) {
            E c = A[i];
            int j = i;
            for (; j > l && c.compareTo(A[j - 1]) < 0; j--) {
                A[j] = A[j - 1];
            }
            A[j] = c;
        }
    }

    public <E extends Comparable<E>> void backSort(E[] A) {
        for (int i = A.length - 2; i >= 0; i--) {
            E c = A[i];
            int j = i;
            for (; j < A.length - 1 && c.compareTo(A[j + 1]) > 0; j++) {
                A[j] = A[j + 1];
            }
            A[j] = c;
        }
    }
}

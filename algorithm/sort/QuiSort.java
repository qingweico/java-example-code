package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/27
 */
public class QuiSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        sort(A, 0, A.length - 1);
    }

    public <E extends Comparable<E>> void sort(E[] A, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(A, l, r);
        sort(A, l, p - 1);
        sort(A, p + 1, r);
    }

    public <E extends Comparable<E>> int partition(E[] A, int l, int r) {
        int j = l;
        // A[l + 1, j] < v
        // A[j + 1, i - 1] >= v
        for (int i = l + 1; i <= r; i++) {
            if (A[i].compareTo(A[l]) < 0) {
                j++;
                Tools.swap(A, i, j);
            }
        }
        Tools.swap(A, l, j);
        return j;
    }
}

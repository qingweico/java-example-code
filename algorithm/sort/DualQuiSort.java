package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/27
 */
public class DualQuiSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        sort(A, 0, A.length - 1);
    }

    private <E extends Comparable<E>> void sort(E[] A, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(A, l, r);
        sort(A, l, p - 1);
        sort(A, p + 1, r);

    }

    private <E extends Comparable<E>> int partition(E[] A, int l, int r) {
        int p = l + (int) (Math.random() * (r - l + 1));
        Tools.swap(A, l, p);
        int i = l + 1;
        int j = r;
        for (; ; ) {
            while (i <= j && A[i].compareTo(A[l]) < 0) {
                i++;
            }
            while (i <= j && A[j].compareTo(A[l]) > 0) {
                j--;
            }
            if (i >= j) break;
            Tools.swap(A, i, j);
            i++;
            j--;
        }
        Tools.swap(A, l, j);
        return j;
    }
}

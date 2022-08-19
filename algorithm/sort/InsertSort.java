package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class InsertSort implements MutableSorter {

    // Has a better performance than InsSort.sort()
    @Override
    public void sort(int[] e) {
        sort(e, 0, e.length);
    }

    public static void sort(int[] A, int l, int r) {
        for (int i = 1; i < r; i++) {
            int c = A[i];
            int j = i;
            for (; j > l && A[j - 1] > c; j--) {
                A[j] = A[j - 1];
            }
            A[j] = c;
        }
    }
}

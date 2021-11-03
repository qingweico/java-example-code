package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/11/3
 */
public class ShellSortImproved {
    public <E extends Comparable<E>> void sort(E[] A) {
        int h = 1;
        int len = A.length;
        // Custom step
        while (h < len) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                E e = A[i];
                int j;
                for (j = i; j - h >= 0 && e.compareTo(A[j - h]) < 0; j -= h) {
                    A[j] = A[j - h];
                }
                A[j] = e;
            }
            h /= 3;
        }
    }
}

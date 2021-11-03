package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/11/3
 */
public class ShellSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        int len = A.length;
        int h = len / 2;
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                E e = A[i];
                int j = i;
                for (; j - h >= 0 && e.compareTo(A[j - h]) < 0; j -= h) {
                    A[j] = A[j - h];
                }
                A[j] = e;
            }
            h /= 2;
        }
    }
}

package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/30
 */
public class BubSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        for (int i = 0; i < A.length - 1; ) {
            int last = 0;
            for (int j = 0; j < A.length - i - 1; j++) {
                if (A[j].compareTo(A[j + 1]) > 0) {
                    Tools.swap(A, j, j + 1);
                    last = j + 1;
                }
            }
            i = A.length - last;
        }
    }

    public <E extends Comparable<E>> void sort2(E[] A) {
        for (int i = 0; i < A.length - 1; ) {
            int last = A.length - 1;
            for (int j = A.length - 1; j > i; j--) {
                if (A[j - 1].compareTo(A[j]) > 0) {
                    Tools.swap(A, j - 1, j);
                    last = j - 1;
                }
            }
            i = last + 1;
        }
    }
}

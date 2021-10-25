package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/16
 */
public class InsertSort implements MutableSorter{

    // Has a better performance than InsSort.sort()
    @Override
    public void sort(int[] A) {
        for (int i = 1; i < A.length; i++) {
            int c = A[i];
            int j = i;
            for (; j > 0 && A[j - 1] > c; j--) {
                A[j] = A[j-1];
            }
            A[j] = c;
        }
    }
}

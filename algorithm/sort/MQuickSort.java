package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/19
 */
public class MQuickSort implements MutableSorter {
    public void sort(int[] A) {
        quickSort(A, 0, A.length);
    }

    private void quickSort(int[] A, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int i = partition(A, l, r);

        quickSort(A, l, i);
        quickSort(A, i + 1, r);
    }

    private int partition(int[] A, int l, int r) {
        int pivot = A[l];
        int i = l + 1;
        int j = r;
        while (i != j) {
            if (A[i] < pivot) {
                i++;
            } else {
                Tools.swap(A, i, --j);
            }
        }
        Tools.swap(A, i - 1, l);
        return i - 1;
    }
}

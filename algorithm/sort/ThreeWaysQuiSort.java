package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/10/27
 */
public class ThreeWaysQuiSort {
    public <E extends Comparable<E>> void sort(E[] A) {
        sort(A, 0, A.length - 1);
    }

    private <E extends Comparable<E>> void sort(E[] A, int l, int r) {
        if (l >= r) {
            return;
        }
        int lt = l, i = l + 1, gt = r + 1;
        while (i < gt) {
            if (A[i].compareTo(A[l]) < 0) {
                lt++;
                Tools.swap(A, i, lt);
                i++;
            } else if (A[i].compareTo(A[l]) > 0) {
                gt--;
                Tools.swap(A, i, gt);
            } else {
                i++;
            }
        }
        // []
        // l ---- lt - 1 +++++  lt ----- gt - 1 +++++ gt ----- r
        Tools.swap(A, l, lt);
        sort(A, l, lt - 1);
        sort(A, gt, r);
    }
}

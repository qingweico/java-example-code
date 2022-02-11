package algorithm.sort;

import java.util.Random;

/**
 * @author zqw
 * @date 2021/10/27
 */
public class QuiSort {
    private static final Random R = new Random();

    public <E extends Comparable<E>> void sort(E[] e) {
        sort(e, 0, e.length - 1);
    }

    public <E extends Comparable<E>> void sort(E[] e, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(e, l, r);
        sort(e, l, p - 1);
        sort(e, p + 1, r);
    }

    public <E extends Comparable<E>> int partition(E[] e, int l, int r) {
        // Generate a random index between l and r(include l and r),
        // to solve the SOF question when sort an ordered array.
        int p = R.nextInt(r - l + 1) + l;
        Tools.swap(e, p, l);
        int j = l;
        // A[l + 1, j] < v
        // A[j + 1, i - 1] >= v
        for (int i = l + 1; i <= r; i++) {
            if (e[i].compareTo(e[l]) < 0) {
                j++;
                Tools.swap(e, i, j);
            }
        }
        Tools.swap(e, l, j);
        return j;
    }
}

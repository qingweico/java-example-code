package algorithm.sort;

import cn.qingweico.supplier.Tools;

import java.util.Random;

/**
 * 双路快排
 * @author zqw
 * @date 2021/10/27
 */
public class DualQuiSort {
    private static final Random R = new Random();

    public <E extends Comparable<E>> void sort(E[] e) {
        sort(e, 0, e.length - 1);
    }

    private <E extends Comparable<E>> void sort(E[] e, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(e, l, r);
        sort(e, l, p - 1);
        sort(e, p + 1, r);

    }

    private <E extends Comparable<E>> int partition(E[] e, int l, int r) {
        int p = l + R.nextInt(r - l + 1);
        Tools.swap(e, l, p);
        int i = l + 1;
        int j = r;
        for (; ; ) {
            // [l + 1, i - 1] <= pivot
            while (i <= j && e[i].compareTo(e[l]) < 0) {
                i++;
            }
            // [j + 1, r] >= pivot
            while (i <= j && e[j].compareTo(e[l]) > 0) {
                j--;
            }
            if (i >= j) {
                break;
            }
            Tools.swap(e, i, j);
            i++;
            j--;
        }
        Tools.swap(e, l, j);
        return j;
    }
}

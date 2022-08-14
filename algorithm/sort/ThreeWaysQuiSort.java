package algorithm.sort;

import util.Tools;

/**
 * @author zqw
 * @date 2021/10/27
 */
public class ThreeWaysQuiSort {
    public <E extends Comparable<E>> void sort(E[] e) {
        sort(e, 0, e.length - 1);
    }

    private <E extends Comparable<E>> void sort(E[] e, int l, int r) {
        if (l >= r) {
            return;
        }
        // [l + 1, lt] < v; [lt + 1, i - 1] == v; [gt, r] > v
        int lt = l, i = l + 1, gt = r + 1;
        while (i < gt) {
            if (e[i].compareTo(e[l]) < 0) {
                lt++;
                Tools.swap(e, i, lt);
                i++;
            } else if (e[i].compareTo(e[l]) > 0) {
                gt--;
                Tools.swap(e, i, gt);
            } else {
                i++;
            }
        }
        // []
        // l ---- lt - 1 +++++  lt ----- gt - 1 +++++ gt ----- r
        Tools.swap(e, l, lt);
        sort(e, l, lt - 1);
        sort(e, gt, r);
    }
}

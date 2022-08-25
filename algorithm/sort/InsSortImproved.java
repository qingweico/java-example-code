package algorithm.sort;


/**
 * @author zqw
 * @date 2021/10/26
 */
public class InsSortImproved {
    public <E extends Comparable<E>> void sort(E[] e) {
        sort(e, 0, e.length);
    }

    public static <E extends Comparable<E>> void sort(E[] e, int l, int r) {
        for (int i = l; i < r; i++) {
            E c = e[i];
            int j = i;
            for (; j > l && c.compareTo(e[j - 1]) < 0; j--) {
                e[j] = e[j - 1];
            }
            e[j] = c;
        }
    }

    public <E extends Comparable<E>> void backSort(E[] e) {
        for (int i = e.length - 2; i >= 0; i--) {
            E c = e[i];
            int j = i;
            for (; j < e.length - 1 && c.compareTo(e[j + 1]) > 0; j++) {
                e[j] = e[j + 1];
            }
            e[j] = c;
        }
    }
}

package algorithm.sort;

/**
 * @author zqw
 * @date 2021/10/16
 */
public class InsertSort implements MutableSorter {

    // Has a better performance than InsSort.sort()

    @Override
    public void sort(int[] e) {
        sort(e, 0, e.length);
    }

    public static void sort(int[] e, int l, int r) {
        for (int i = 1; i < r; i++) {
            int c = e[i];
            int j = i;
            for (; j > l && e[j - 1] > c; j--) {
                e[j] = e[j - 1];
            }
            e[j] = c;
        }
    }
}

package algorithm.sort;

import util.Tools;

/**
 * 插入排序
 *
 * @author zqw
 * @date 2021/10/25
 */
public class InsSort {
    public <E extends Comparable<E>> void sort(E[] e) {
        sort(e, 0, e.length);
    }

    public <E extends Comparable<E>> void backSort(E[] e) {
        for (int i = e.length - 2; i >= 0; i--) {
            for (int j = i; j < e.length - 1 && e[j].compareTo(e[j + 1]) > 0; j++) {
                Tools.swap(e, j, j + 1);
            }
        }
    }

    public static <E extends Comparable<E>> void sort(E[] e, int l, int r) {
        for (int i = 1; i < r; i++) {
            for (int j = i; j > l && e[j].compareTo(e[j - 1]) < 0; j--) {
                Tools.swap(e, j, j - 1);
            }
        }
    }
}

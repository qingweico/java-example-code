package algorithm.sort;

import util.Tools;

/**
 * 选择排序
 *
 * @author zqw
 * @date 2021/10/25
 */
public class SelSort {

    public <E extends Comparable<E>> void sort(E[] e) {
        for (int i = 0; i < e.length; i++) {
            int min = i;
            for (int j = i + 1; j < e.length; j++) {
                if (e[j].compareTo(e[min]) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                Tools.swap(e, i, min);
            }
        }
    }
}

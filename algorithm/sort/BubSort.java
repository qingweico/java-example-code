package algorithm.sort;

import cn.qingweico.supplier.Tools;

/**
 * @author zqw
 * @date 2021/10/30
 */
public class BubSort {
    public <E extends Comparable<E>> void sort(E[] e) {
        for (int i = 0; i < e.length - 1; ) {
            int last = 0;
            for (int j = 0; j < e.length - i - 1; j++) {
                if (e[j].compareTo(e[j + 1]) > 0) {
                    Tools.swap(e, j, j + 1);
                    last = j + 1;
                }
            }
            i = e.length - last;
        }
    }

    public <E extends Comparable<E>> void sort2(E[] e) {
        for (int i = 0; i < e.length - 1; ) {
            int last = e.length - 1;
            for (int j = e.length - 1; j > i; j--) {
                if (e[j - 1].compareTo(e[j]) > 0) {
                    Tools.swap(e, j - 1, j);
                    last = j - 1;
                }
            }
            i = last + 1;
        }
    }
}

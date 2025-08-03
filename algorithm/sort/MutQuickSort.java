package algorithm.sort;

import cn.qingweico.supplier.Tools;

/**
 * @author zqw
 * @date 2021/10/19
 */
public class MutQuickSort implements MutableSorter {
    @Override
    public void sort(int[] e) {
        quickSort(e, 0, e.length);
    }

    private void quickSort(int[] e, int l, int r) {
        if (r - l <= 1) {
            return;
        }
        int i = partition(e, l, r);

        quickSort(e, l, i);
        quickSort(e, i + 1, r);
    }

    private int partition(int[] e, int l, int r) {
        int pivot = e[l];
        int i = l + 1;
        int j = r;
        while (i != j) {
            if (e[i] < pivot) {
                i++;
            } else {
                Tools.swap(e, i, --j);
            }
        }
        Tools.swap(e, i - 1, l);
        return i - 1;
    }
}

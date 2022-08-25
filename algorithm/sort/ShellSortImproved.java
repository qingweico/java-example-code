package algorithm.sort;

/**
 * @author zqw
 * @date 2021/11/3
 */
public class ShellSortImproved {
    public <E extends Comparable<E>> void sort(E[] eArray) {
        int h = 1;
        int len = eArray.length;
        // Custom step
        while (h < len) {
            h = h * 3 + 1;
        }
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                E e = eArray[i];
                int j;
                for (j = i; j - h >= 0 && e.compareTo(eArray[j - h]) < 0; j -= h) {
                    eArray[j] = eArray[j - h];
                }
                eArray[j] = e;
            }
            h /= 3;
        }
    }
}

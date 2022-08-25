package algorithm.sort;

/**
 * @author zqw
 * @date 2021/11/3
 */
public class ShellSort {
    public <E extends Comparable<E>> void sort(E[] eArray) {
        int len = eArray.length;
        int h = len / 2;
        while (h >= 1) {
            for (int i = h; i < len; i++) {
                E e = eArray[i];
                int j = i;
                for (; j - h >= 0 && e.compareTo(eArray[j - h]) < 0; j -= h) {
                    eArray[j] = eArray[j - h];
                }
                eArray[j] = e;
            }
            h /= 2;
        }
    }
}

package array;

import java.util.Arrays;

/**
 * @author:qiming
 * @date: 2021/1/2
 */
public class ComparatorUsage {

    public static void main(String[] args) {
        Integer[] a = new Integer[]{1, 8, 2, 0};
        Arrays.sort(a, (o1, o2) -> Integer.bitCount(o1) > Integer.bitCount(o2) ? o1 - o2 : o2 - o1);
        System.out.println(Arrays.toString(a));
    }

}

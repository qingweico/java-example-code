package algorithm.sort;

import java.util.Arrays;

/**
 * LSD: Least Significant Digit
 *
 * @author zqw
 * @date 2021/11/17
 */
public class LsdSort {

    public void sort(String[] e) {
        int max = 256;
        int w = e[0].length();
        int n = e.length;
        int[] cnt = new int[max];
        int[] index = new int[max + 1];
        String[] help = new String[n];

        for (int r = w - 1; r >= 0; r--) {
            Arrays.fill(cnt, 0);
            for (String s : e) {
                cnt[s.charAt(r)]++;
            }

            for (int i = 0; i < max; i++) {
                index[i + 1] = index[i] + cnt[i];
            }

            for (String s : e) {
                help[index[s.charAt(r)]++] = s;
            }
            System.arraycopy(help, 0, e, 0, n);
        }
    }
}
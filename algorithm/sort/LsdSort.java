package algorithm.sort;

import java.util.Arrays;

/**
 * LSD: Least Significant Digit
 *
 * @author:qiming
 * @date: 2021/11/17
 */
public class LsdSort {

    public void sort(String[] A) {
        int R = 256;
        int w = A[0].length();
        int n = A.length;
        int[] cnt = new int[R];
        int[] index = new int[R + 1];
        String[] help = new String[n];

        for (int r = w - 1; r >= 0; r--) {
            Arrays.fill(cnt, 0);
            for (String s : A) {
                cnt[s.charAt(r)]++;
            }

            for (int i = 0; i < R; i++) {
                index[i + 1] = index[i] + cnt[i];
            }

            for (String s : A) {
                help[index[s.charAt(r)]++] = s;
            }
            System.arraycopy(help, 0, A, 0, n);
        }
    }
}
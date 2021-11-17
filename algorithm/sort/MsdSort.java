package algorithm.sort;

/**
 * @author:qiming
 * @date: 2021/11/16
 */
public class MsdSort {
    public static void sort(String[] A) {
        String[] help = new String[A.length];
        sort(A, 0, A.length - 1, 0, help);
    }

    private static void sort(String[] A, int l, int r, int k, String[] help) {
        if (l >= r) return;
        int R = 256;
        int[] cnt = new int[R + 1];
        int[] index = new int[R + 2];
        for (int i = l; i <= r; i++) {
            int p = k >= A[i].length() ? 0 : A[i].charAt(k) + 1;
            cnt[p]++;
        }
        for (int i = 0; i < R + 1; i++) {
            index[i + 1] = index[i] + cnt[i];
        }
        for (int i = l; i <= r; i++) {
            int p = k >= A[i].length() ? 0 : (A[i].charAt(k) + 1);
            help[l + index[p]] = A[i];
            index[p]++;
        }
        if (r + 1 - l >= 0) System.arraycopy(help, l, A, l, r + 1 - l);
        for (int i = 0; i < R; i++) {
            sort(A, l + index[i], l + index[i + 1] - 1, k + 1, help);
        }
    }
}

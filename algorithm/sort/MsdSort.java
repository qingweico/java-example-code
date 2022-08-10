package algorithm.sort;

/**
 * @author zqw
 * @date 2021/11/16
 */
class MsdSort {
    public static void sort(String[] e) {
        String[] help = new String[e.length];
        sort(e, 0, e.length - 1, 0, help);
    }

    private static void sort(String[] e, int l, int r, int k, String[] help) {
        if (l >= r) {
            return;
        }
        int mr = 256;
        int[] cnt = new int[mr + 1];
        int[] index = new int[mr + 2];
        for (int i = l; i <= r; i++) {
            int p = k >= e[i].length() ? 0 : e[i].charAt(k) + 1;
            cnt[p]++;
        }
        for (int i = 0; i < mr + 1; i++) {
            index[i + 1] = index[i] + cnt[i];
        }
        for (int i = l; i <= r; i++) {
            int p = k >= e[i].length() ? 0 : (e[i].charAt(k) + 1);
            help[l + index[p]] = e[i];
            index[p]++;
        }
        if (r + 1 - l >= 0) {
            System.arraycopy(help, l, e, l, r + 1 - l);
        }
        for (int i = 0; i < mr; i++) {
            sort(e, l + index[i], l + index[i + 1] - 1, k + 1, help);
        }
    }
}

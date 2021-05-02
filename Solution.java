

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author:qiming
 * @date: 2021/3/27
 */
public class Solution {

    public static int strStr(String haystack, String needle) {
        if ("".equals(needle)) return 0;
        int[] next = new int[needle.length()];
        for (int i = 1, j = 0; i < needle.length(); i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) j = next[j - 1];
            if (needle.charAt(i) == needle.charAt(j)) j++;
            next[i] = j;
        }
        for (int i = 0, j = 0; i < haystack.length(); i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) j = next[j - 1];
            if (haystack.charAt(i) == needle.charAt(j)) j++;
            if (j == needle.length()) return i - j + 1;
        }
        return -1;
    }

    public static void maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; ++i) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        System.out.println(Arrays.deepToString(dp));
    }


    public static String minWindow(String s, String t) {
        int[] ch = new int[128];
        boolean[] bool = new boolean[128];
        int l = 0, minL = 0, minLen = s.length() + 1, count = 0, res = 0;
        for (int i = 0; i < t.length(); i++) {
            bool[t.charAt(i)] = true;
            ch[t.charAt(i)]++;
        }
        System.out.println(Arrays.toString(ch));
        for (int r = 0; r < s.length(); r++) {

            // TODO
            if (bool[s.charAt(r)]) {
                if (--ch[s.charAt(r)] >= 0) {
                    ++count;
                    System.out.println(Arrays.toString(ch));

                }
                while (count == t.length()) {
                    if (r - l + 1 < minLen) {
                        minL = l;
                        minLen = r - l + 1;
                    }
                    if (bool[s.charAt(l)] && ++ch[s.charAt(l)] > 0) --count;
                    l++;
                }
            }
        }
        System.out.println(res);
        return minLen > s.length() ? "" : s.substring(minL, minLen + minL);


    }

    static void merge_sort(int[] nums, int l, int r, int[] temp) {
        if (l + 1 >= r) {
            return;
        }
        int m = l + (r - l) / 2;
        merge_sort(nums, l, m, temp);
        merge_sort(nums, m, r, temp);
// conquer
        int p = l, q = m, i = l;
        while (p < m || q < r) {
            if (q >= r || (p < m && nums[p] <= nums[q])) {
                temp[i++] = nums[p++];
            } else {
                temp[i++] = nums[q++];
            }
        }
        for (i = l; i < r; ++i) {
            nums[i] = temp[i];
        }
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> hashMap = new HashMap<>();
        int[] res = new int[k];
        int maxCount = 0;
        for (int n : nums) {
            maxCount = Math.max(maxCount, hashMap.getOrDefault(n, 0) + 1);
            hashMap.put(n, hashMap.getOrDefault(n, 0) + 1);

        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(hashMap.entrySet());
        list.sort(((o1, o2) -> o2.getValue().compareTo(o1.getValue())));
        for (int i = 0; i < k; i++) {
            res[i] = list.get(i).getKey();
        }
        return res;

    }
    public static void main(String[] args) {
        maxProfit(new int[]{7, 1, 5, 4, 6, 4});
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));

    }

}

enum AccountType {
    SAVING, FIXED, CURRENT;

    AccountType() {
        System.out.println("It is a account type");
    }
}

class EnumOne {
    public static void main(String[] args) {
        System.out.println(AccountType.FIXED);
    }
}

class Test {
    public static void main(String[] args) {
        System.out.println(new B().getValue());
    }

    static class A {
        protected int value;

        public A(int v) {
            setValue(v);
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            try {
                value++;
                return value;
            } finally {
                this.setValue(value);
                System.out.println(value);
            }
        }
    }

    static class B extends A {
        public B() {
            super(5);
            setValue(getValue() - 3);
        }

        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }
}

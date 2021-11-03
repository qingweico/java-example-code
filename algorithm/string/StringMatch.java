package algorithm.string;

/**
 * @author:qiming
 * @date: 2021/11/3
 */
public class StringMatch {
    public static int match(String s, String t) {
        if (s.length() < t.length()) return -1;
        for (int i = 0; i + t.length() - 1 < s.length(); i++) {
            int j = 0;
            for (; j < t.length(); j++) {
                if (s.charAt(i + j) != t.charAt(j)) {
                    break;
                }
            }
            if (j == t.length()) return i;
        }
        return -1;
    }
}

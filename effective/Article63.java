package effective;

/**
 * 了解字符串的连接性能
 *
 * @author:qiming
 * @date: 2021/3/26
 */
// Repeatedly using the string concatenation operator to
// concatenate n strings takes n orders of squares of time.
public class Article63 {
    private final int numItems = 10;
    private static final int LINE_WIDTH = 10;
    // Inappropriate use of string concatenation - Performs poorly!
    // Bad!
    public String statement() {
        String result = "";
        for(int i = 0; i < numItems; i++) {
            result += lineForItem(i);
        }
        return result;
    }
    private String lineForItem(int i) {
        return String.valueOf(i);
    }
    // Using StringBuilder instead of String!
    public String statement0() {
        StringBuilder res = new StringBuilder(numItems * LINE_WIDTH);
        for(int i = 0;i < numItems;i++) {
            res.append(lineForItem(i));
        }
        return res.toString();
    }

    // Or using char array.

}


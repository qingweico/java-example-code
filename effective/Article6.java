package effective;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * 避免创建不必要的对象
 *
 * @author:qiming
 * @date: 2021/3/25
 */
public class Article6 {
    public static void main(String[] args) {

        // Don't do this!
        String s = new String("biniki");


        // Static factory methods always take precedence over constructors.
        Boolean bool = Boolean.valueOf("true");

    }
    // Performance can be greatly improved!
    // Although the String.matches method is the easiest way to see if a String matches a regular
    // expression, it is not suitable for repeated use in performance-focused situations.
    // 1345us
    static boolean isRomanNumeral(String s) {
        // It internally creates an instance of Pattern for the regular expression,
        // but uses it once, examples of Pattern are expensive to create.
        return s.matches("^(?=.)M*(C[MD] | D?C* 3})(X[CL] | L?X* 3})(I[XV] | V?I* 3})$");
    }
}
// Reusing expensive object for improved performance
 class RomanNumeral {
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD] | D?C* 3})(X[CL] | L?X* 3})(I[XV] | V?I* 3})$");
    // 169us
    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }

    public static void main(String[] args) {
        // The keySet method returns the Set view of the Map object
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        Set<Integer> set = map.keySet();
        set.remove(1);
        // {2=2, 3=3}
        System.out.println(map);

    }
 }

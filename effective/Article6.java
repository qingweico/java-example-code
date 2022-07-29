package effective;

import annotation.Pass;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 避免创建不必要的对象
 *
 * @author zqw
 * @date 2021/3/25
 */
@Pass
@SuppressWarnings("all")
class Article6 {
    public static void main(String[] args) {

        // Don't do this!
        String s = new String("bin-iki");


        // Static factory methods always take precedence over constructors.
        Boolean bool = Boolean.valueOf("true");

    }

    /**
     * Performance can be greatly improved!
     * Although the String::matches method is the easiest way to see if a String matches a regular
     * expression, it is not suitable for repeated use in performance-focused situations.
     * time spend: 2523.5us
     *
     * @param s String
     * @return s is or not roman-number
     */
    static boolean isRomanNumeral(String s) {
        // It internally creates an instance of Pattern for the regular expression,
        // but uses it once, examples of Pattern are expensive to create.
        return s.matches("^(?=.)M*(C[MD] | D?C* 3})(X[CL] | L?X* 3})(I[XV] | V?I* 3})$");
    }

}

/**
 * Reusing expensive object for improved performance
 */
class RomanNumeral {
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD] | D?C* 3})(X[CL] | L?X* 3})(I[XV] | V?I* 3})$");

    /**
     * time spend: 394.4us
     *
     * @param s String
     * @return s is or not roman-number
     */
    static boolean isRomanNumeral(String s) {
        return ROMAN.matcher(s).matches();
    }

    public static void main(String[] args) {
        // The keySet method returns the Set view of the Map object
        Map<Integer, String> map = new HashMap<>(3);
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        Set<Integer> set = map.keySet();
        set.remove(1);
        // {2=2, 3=3}
        System.out.println(map);
        System.out.println(isRomanNumeral("XV"));
        System.out.println(isRomanNumeral("III"));
    }
}

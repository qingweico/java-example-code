package thinking.string;

import cn.qingweico.io.Print;

import java.util.Arrays;

/**
 * When the contents of a String need to be changed, a String method returns a new String
 * object, if the contents have not been changed, a String method simply returns a
 * reference to the original object.
 *
 * @author zqw
 * @date 2021/2/5
 */
class OperateString {
    public static void main(String[] args) {
        String s = "you go first";

        // getChars
        char[] chars = new char[10];
        // [)
        s.getChars(0, 5, chars, 1);
        Print.println(Arrays.toString(chars));

        // getBytes
        byte[] bv = s.getBytes();
        Print.println(Arrays.toString(bv));

        // Returns true if the String is exactly the same as the parameter.
        Print.println(s.contentEquals("you go first"));

        // Returns equality of comparison areas.
        // Overloaded moderator adds the ability to ignore case.
        String other = "here you are";
        Print.println(s.regionMatches(0, other, 5, 3));


        // [beginIndex, endIndex)
        Print.println(s.substring(0, 5));


        Print.println(s.concat("!"));

        Print.println(String.valueOf(new char[]{'b', 'a', 'c', 'k'}));

        Print.println(String.valueOf(new Object()).indexOf('@'));

        // Generates one and only one String reference for each unique sequence of characters,
        // in other words, adds the String to the constant pool and returns the reference,
        // or if the String already exists in the pool.
        Print.println(s.intern());
    }
}

package thinking.string;

import static util.Print.print;
import static util.Print.printnb;

/**
 * override: An operator is given a particular meaning when applied to
 * a particular class.
 * *
 * @author:周庆伟
 * @date: 2021/1/19
 */
// The '+' and "+=" operators for Sting are the only two overloaded operators in Java.
public class Immutable {
    public static String upcase(String s){
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        String q = "hello";
        String p = upcase(q);
        print(q);
        printnb(p);
    }
}

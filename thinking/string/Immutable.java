package thinking.string;

import static util.Print.print;
import static util.Print.printnb;

/**
 * override: An operator is given a particular meaning when applied to
 * a particular class.
 * The '+' and "+=" operators for Sting are the only two overloaded operators in Java.
 * @author zqw
 * @date 2021/1/19
 */
class Immutable {
    public static String upCase(String s){
        return s.toUpperCase();
    }

    public static void main(String[] args) {
        String q = "hello";
        String p = upCase(q);
        print(q);
        printnb(p);
    }
}

package jvm;

import org.junit.Test;
import cn.qingweico.io.Print;

/**
 * String::intern 方法测试
 *
 * @author zqw
 * @date 2021/3/27
 */
public class StringInternTest {
    @Test
    public void testFinal() {
        final String a = "a";
        final String b = "b";
        String s = "ab";
        // true
        // Using string constant or constant ref not use StringBuilder
        Print.println(s == a + b);
    }

    @Test
    public void testIntern() {
        // Using the string concatenation operator does not generate an object in the
        // string constant pool, whereas using new String() will generate two objects,
        // one on the heap and one in the String constant pool.
        String s10 = new String("a") + new String("b");
        s10.intern();
        String s12 = "ab";
        // true
        Print.println(s10 == s12);
    }

    public static void main(String[] args) {
        String s = new String("a");
        String ss = s.intern();
        String str = "a";
        // false
        Print.println(s == str);
        // true
        Print.println(ss == str);


        String s1 = new String("a") + new String("b");
        // A new object is not created in the string constant pool because it is already
        // in the heap, so a copy of the reference address of the object is simply copied
        // into the constant pool.
        s1.intern();
        String s2 = "ab";
        // true
        Print.println(s1 == s2);


        String s3 = new String("a") + new String("b");
        String s4 = "ab";
        String s5 = s3.intern();
        // false
        Print.println(s3 == s4);
        // true
        Print.println(s5 == s4);


        String s6 = "ab";
        String s7 = new String("a") + new String("b");
        String s8 = s7.intern();
        // false
        Print.println(s6 == s7);
        // true
        Print.println(s6 == s8);


        String s9 = new String("ab");
        s9.intern();
        String s11 = "ab";
        // false
        Print.println(s9 == s11);

        String a = new String("abc") + new String("def");
        String b = "abcdef";
        // true
        Print.println(a.intern() == b);

    }
}

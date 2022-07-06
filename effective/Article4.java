package effective;

import java.lang.reflect.Constructor;

/**
 * 通过私有构造器强化不可实例化的能力
 *
 * @author zqw
 * @date 2021/3/22
 */
class Article4 {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("effective.UtilityClass");
        Constructor<?> c = cls.getDeclaredConstructor();
        c.setAccessible(true);
        System.out.println(c.newInstance());
    }
}

/**
 * java.lang.Math java.util.Arrays java.util.Collections
 * Such utility classes do not want to be instantiated, because instantiation
 * doesn't mean anything to them.
 * Attempting to make a class abstract to force it not to be instantiated does not work,
 * because the class can be subclassed, and that subclass can also be instantiated,
 * so as long as the class contains a private constructor, it cannot be instantiated.
 */

class UtilityClass {
    /**
     * Suppress default construct for non-instantiable
     */
    private UtilityClass() {
        System.err.println("private constructor!");
        throw new AssertionError();
    }

    // Remainder omitted
}

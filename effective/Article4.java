package effective;

/**
 * 通过私有构造器强化不可实例化的能力
 *
 * @author:qiming
 * @date: 2021/3/22
 */

// java.lang.Math java.util.Arrays java.util.Collections
// Such utility classes do not want to be instantiated, because instantiation
// doesn't mean anything to them.
// Attempting to make a class abstract to force it not to be instantiated does not work,
// because the class can be subclassed, and that subclass can also be instantiated.
// So as long as the class contains a private constructor, it cannot be instantiated.
public class Article4 {

}
class UtilityClass {
    // Suppress default construct for non-instantiability
    private UtilityClass() {
        throw new AssertionError();
    }

    // Remainder omitted
}

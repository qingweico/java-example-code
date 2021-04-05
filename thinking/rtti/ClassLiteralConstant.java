package thinking.rtti;

/**
 * A reference to a Class object can also be generated using Class literal constants,
 * not only is it simpler, but it's also more secure,because it will be checked when
 * it compiles.
 * @author:qiming
 * @date: 2021/2/7
 */
public class ClassLiteralConstant {
    public static void main(String[] args) {

        // The Type field is a reference to a Class object of the corresponding primitive data TYPE.
        // boolean.class -> Boolean.TYPE
        // char.class -> Character.TYPE
        // ...
    }
}

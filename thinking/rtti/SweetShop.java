package thinking.rtti;

/**
 * All classes are dynamically loaded into the JVM the first time they are used,
 * the class is loaded when the program creates the first reference to a static
 * member of the class. This proof constructor is also a static method of the class,
 * even if the static keyword was not used before the constructor.
 *
 * A Java program is not fully loaded before it starts running, its parts are loaded
 * only when needed
 *
 * @author:qiming
 * @date: 2021/1/16
 */
public class SweetShop {
    public static void main(String[] args) {
        System.out.println("inside main");
        new Candy();
        System.out.println("After create Candy");
        try {
            // forName () returns a reference to the Class object, which is
            // called for its side effect.
            // tips: In the string passed to forName(), you must use the fully
            // qualified class name (including the package name).
            Class c = Class.forName("thinking.rtti.Gum");

            // Call the getClass method to get a Class reference, which represents
            // a Class reference to the actual type of the object.

        } catch (ClassNotFoundException e) {
            System.out.println("Could't find Gum");
        }
        System.out.println("After Class.forName(\"Gum\")");
        new Cookie();
        System.out.println("After creating Cookie");
    }
}
class Candy{
    static {
        System.out.println("Loading Candy");
    }
}
class Gum{
    static {
        System.out.println("Loading Gum");
    }
}
class Cookie{
    static {
        System.out.println("Loading Cookie");
    }
}
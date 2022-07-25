package object;

import static util.Print.print;

/**
 * Method hiding and method overriding
 * <p>
 * RTTI: Run-Time Type Identification
 *
 * @author zqw
 * @date 2020/12/12
 */

class MethodHideAndCover {
    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        // A child class does not override the static methods of its parent class,
        // which is not visible to the child class, this is called hiding.
        Father father = new Son();
        print(father.a);
        print(father.b);
        father.c();
        father.d();
    }

}

class Father {

    // All member variables (static or non-static) are statically bound only, Therefore,
    // the binding result of the JVM would be to directly access the member variable
    // of the static type, that is, the member variable of the parent class, and output 0.

    static int a = 0;

    int b = 0;

    // For non-static methods, dynamic binding is performed. The JVM checks out the dynamic type
    // that refers to Father, namely Son class, and the binding result is: call the method in the
    // subclass, and output 1.

    void c() {
        print(0);
    }

    // For static methods, only static binding is performed, so the JVM will bind by referring to
    // the static type, namely Father class. The result is: directly call the static method in the
    // parent class, and output 0.

    static void d() {
        print(0);
    }
}

/**
 * Declare subclass
 */
@SuppressWarnings("unused")
class Son extends Father {
    static int a = 1;
    int b = 1;

    @Override
    void c() {
        print(1);
    }

    static void d() {
        print(1);
    }
}

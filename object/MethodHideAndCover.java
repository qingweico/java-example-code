package object;

/**
 * Method hiding and method overriding
 * <p>
 * RTTI Run-Time Type Identification 运行时类型识别
 *
 * @author:qiming
 * @date: 2020/12/12
 */

public class MethodHideAndCover {
    public static void main(String[] args) {
        // A child class does not override the static methods of its parent class, which is not
        // visible to the child class. This is called hiding.
        Father father = new Son();
        System.out.println(father.a);
        System.out.println(father.b);
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
        System.out.println(0);
    }

    // For static methods, only static binding is performed, so the JVM will bind by referring to
    // the static type, namely Father class. The result is: directly call the static method in the
    // parent class, and output 0.

    static void d() {
        System.out.println(0);
    }
}

/**
 * 声明子类
 */
class Son extends Father {
    static int a = 1;
    int b = 1;

    @Override
    void c() {
        System.out.println(1);
    }

    static void d() {
        System.out.println(1);
    }
}

package jvm.classloading;

import org.junit.Test;

/**
 * @author:qiming
 * @date: 2021/11/19
 */
public class NoInitialization {
    // 子类引用父类的静态字段, 不会导致子类初始化
    @Test
    public void staticRef() {
        System.out.println(SubClass.value);

    }

    // 通过数组来引用类, 不会触发此类的初始化
    @Test
    public void arrayRef() {
        SuperClass[] sca = new SuperClass[10];
    }

    @Test
    public void staticFinalConstant() {
        System.out.println(ConstClass.HELLO_WORLD);
    }
}

// -XX:+TraceClassLoading
class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }

    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init!");
    }
}

class ConstClass {
    static {
        System.out.println("ConstClass init!");
    }

    public final static String HELLO_WORLD = "hello world";
}

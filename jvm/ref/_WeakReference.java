package jvm.ref;

import java.lang.ref.WeakReference;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/9/30
 */
public class _WeakReference {
    public static void main(String[] args) {

        Object o1 = new Object();
        WeakReference<Object> ref = new WeakReference<>(o1);

        print(o1);
        print(ref.get());
        print("----------");

        o1 = null;
        System.gc();

        print(o1);
        print(ref.get());
    }
}

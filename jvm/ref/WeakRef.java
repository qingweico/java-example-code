package jvm.ref;

import cn.qingweico.io.Print;

import java.lang.ref.WeakReference;

/**
 * @author zqw
 * @date 2021/9/30
 */
public class WeakRef {
    public static void main(String[] args) {

        Object o1 = new Object();
        WeakReference<Object> ref = new WeakReference<>(o1);

        Print.println(o1);
        Print.println(ref.get());
        Print.println("----------");

        o1 = null;
        System.gc();

        Print.println(o1);
        Print.println(ref.get());
    }
}

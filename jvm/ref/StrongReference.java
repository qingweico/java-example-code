package jvm.ref;

/**
 * @author:qiming
 * @date: 2021/9/30
 */
public class StrongReference {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = o1;
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(o2);
    }
}

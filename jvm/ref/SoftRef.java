package jvm.ref;

import java.lang.ref.SoftReference;

/**
 * @author zqw
 * @date 2021/9/30
 */
public class SoftRef {

    public static void main(String[] args) {
        Object o1 = new Object();
        SoftReference<Object> ref = new SoftReference<>(o1);
        System.out.println(o1);
        System.out.println("softRef: " + ref.get());
        o1 = null;
        try {
            // -Xms5m -Xmx5m -XX:+PrintGCDetails
            var bytes = new byte[10 * 1024 * 1024];
        } finally {
            System.out.println(o1);
            System.out.println("softRef: " + ref.get());
        }
    }
}

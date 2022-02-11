package jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class ObjectLayout {
    static final Object O = new Object();

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseInstance(O).toPrintable());

        synchronized (O) {
            System.out.println(ClassLayout.parseInstance(O).toPrintable());
        }

    }
}

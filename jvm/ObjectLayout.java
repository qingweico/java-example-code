package jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author zqw
 * @date 2021/3/5
 */
public class ObjectLayout {
    static final Object LOCK = new Object();

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseInstance(LOCK).toPrintable());

        synchronized (LOCK) {
            System.out.println(ClassLayout.parseInstance(LOCK).toPrintable());
        }

    }
}

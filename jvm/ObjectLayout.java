package jvm;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author:qiming
 * @date: 2021/3/5
 */
public class ObjectLayout {
    static final Object o = new Object();

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }
}

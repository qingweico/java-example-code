package jvm;

import org.openjdk.jol.info.ClassLayout;
/**
 * 打印编译的细节 {@code -XX:+PrintCompilation}
 * 输出更多的编译细节 {@code -XX:UnlockDiagnosticVMOptions
 * -XX:+LogCompilation -XX:Logfile=<your_file_path>[可选] or output hotspot_pid<pid>.log}
 *
 *
 * 对象布局
 * @author zqw
 * @date 2021/3/5
 * @see EmptyObject
 */
class ObjectLayout {
    static final Object O = new Object();

    public static void main(String[] args) {

        System.out.println(ClassLayout.parseInstance(O).toPrintable());

        synchronized (O) {
            System.out.println(ClassLayout.parseInstance(O).toPrintable());
        }

    }
}

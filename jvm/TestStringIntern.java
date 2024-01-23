package jvm;

import annotation.Pass;
import util.Print;
import util.constants.Constants;
import util.Tools;

import java.util.concurrent.TimeUnit;

/**
 * 使用 String::intern 使 Java 方法区内存溢出(JDK8 字符串常量池是在方法区中)
 *
 * @author zqw
 * @date 2021/3/27
 */
@SuppressWarnings("all")
@Pass
class TestStringIntern {
    // VM: -Xmx15m -Xms15m

    private static final int MAX_COUNT = Constants.NUM_1000000;
    private static final String[] A = new String[MAX_COUNT];

    public static void main(String[] args) {
        int[] data = Tools.genIntArray(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            // remove intern(); try again!
            A[i] = new String(String.valueOf(data[i % data.length]));
        }
        Print.time("", System.currentTimeMillis() - start);
        try {
            TimeUnit.SECONDS.sleep(Constants.NUM_10000);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }
        System.gc();
    }
}

package jvm;

import util.Print;

/**
 * Java 后端编译执行模式
 *
 * {@code java -Xint -version}: Enable Explain Execution Mode
 * {@code java -Xcomp -version}: Enable compilation execution mode
 *
 * @author zqw
 * @date 2021/1/18
 */
class ExecutionMode {
    public static void main(String[] args) {
        Print.println(System.getProperty("java.version"));
        Print.println(countDepth());
        Print.println(countDepth());
        Print.println(countDepth());
        Print.println(countDepth());
        Print.println(countDepth());
        Print.println(countDepth());
        Print.println(countDepth());
    }

    static int countDepth() {
        try {
            return 1 + countDepth();
        } catch (StackOverflowError err) {
            return 0;
        }
    }
}

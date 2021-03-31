package jvm;

import static util.Print.print;

/**
 * @author:qiming
 * @date: 2021/1/18
 */
public class ExecutionMode {
    // java -Xint -version: Enable Explain Execution Mode
    // java -Xcomp -version: Enable compilation execution mode
    public static void main(String[] args) {
        print(System.getProperty("java.version"));

        print(countDepth());

        print(countDepth());

        print(countDepth());

        print(countDepth());

        print(countDepth());

        print(countDepth());

        print(countDepth());


    }

    static int countDepth() {

        try {
            return 1 + countDepth();
        } catch (StackOverflowError err) {
            return 0;
        }

    }


}

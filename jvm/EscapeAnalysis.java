package jvm;

import object.entity.Student;
import util.Print;
import util.constants.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Escape Analysis
 *
 * @author zqw
 * @date 2021/2/19
 */
class EscapeAnalysis {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < Constants.NUM_100000000; i++) {
            alloc();
        }
        Print.time("time", System.currentTimeMillis() - start);
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Print.err(e.getMessage());
        }

    }

    public static void alloc() {
        @SuppressWarnings("unused")
        Student student = new Student();
    }
}

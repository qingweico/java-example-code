package jvm;

import util.Constants;

import java.util.concurrent.TimeUnit;

/**
 * Escape Analysis
 *
 * @author zqw
 * @date 2021/2/19
 */
public class EscapeAnalysis {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < Constants.HUNDRED_MILLION; i++) {
            alloc();
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void alloc() {
        User user = new User();
    }

    static class User {
    }
}

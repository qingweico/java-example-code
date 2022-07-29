package jvm;

import util.Constants;

import java.util.concurrent.TimeUnit;

/**
 * @author zqw
 * @date 2021/3/27
 */
public class TestStringIntern {
    private static final int MAX_COUNT = Constants.NUM_1000000;
    private static final String[] ARR = new String[MAX_COUNT];

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        long start = System.currentTimeMillis();
        for (int i = 0; i < MAX_COUNT; i++) {
            // remove intern(); try again!
            ARR[i] = new String(String.valueOf(data[i % data.length])).intern();
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        try {
            TimeUnit.SECONDS.sleep(Constants.NUM_10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
    }

}

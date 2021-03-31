package jvm;

import java.util.concurrent.TimeUnit;

/**
 * @author:qiming
 * @date: 2021/3/27
 */
public class TestStringIntern {
    private static final int MAX_COUNT = 1_000_000;
    private static final String[] arr = new String[MAX_COUNT];

    public static void main(String[] args) {
        int[] data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9 ,10};
        long start = System.currentTimeMillis();
        for(int i = 0;i < MAX_COUNT;i++) {
            arr[i] = new String(String.valueOf(data[i % data.length])).intern();
            //arr[i] = new String(String.valueOf(data[i % data.length]));
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        try {
            TimeUnit.SECONDS.sleep(10_000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.gc();
    }

}

package oak.base;

import java.util.Arrays;
import java.util.Random;

/**
 * @author zqw
 * @date 2021/2/10
 */
class ArraysToString {
    static Random rand = new Random();
    public static void main(String[] args) {
        byte[] by = new byte[10];
        // 生成随机字节数组
        rand.nextBytes(by);
        System.out.println(Arrays.toString(by));
    }
}

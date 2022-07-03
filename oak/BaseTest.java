package oak;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * 更多测试请参考微基准测试工具jmh
 * @author zqw
 * @date 2022/2/3
 */
@Slf4j
public class BaseTest {
    @Test
    public void floatNumber() {
        float positive = 0.0F;
        float negative = -0.0F;
        // Float.NaN
        // [0x7F800001, 0x7FFFFFFF]
        // [0xFF800001, 0xFFFFFFFF]
        // Standard NaN: 0x7FC00000
        // positive == negative is true
        System.out.println(positive * negative);
        System.out.println(0x7FC00000);
        // -0.0F
        System.out.println(Float.intBitsToFloat(0x80000000));
        float positiveInfinity = 0x7F800000;
        float negativeInfinity = 0xFF800000;
        System.out.println(positiveInfinity);
        System.out.println(negativeInfinity);
        System.out.println(0x7F800001);
        System.out.println(0x7FFFFFFF);
        System.out.println(Float.NEGATIVE_INFINITY);
        System.out.println(Float.POSITIVE_INFINITY);
    }

    @Test
    public void bitComparable() {
        Integer[] a = new Integer[]{1, 8, 2, 0};
        Arrays.sort(a, (o1, o2) -> Integer.bitCount(o1) > Integer.bitCount(o2) ? o1 - o2 : o2 - o1);
        System.out.println(Arrays.toString(a));
    }
    @Test
    public void outBinary() {
        byte aByte = (byte) 0b00100001;
        System.out.println(aByte);
    }
    @Test
    public void bitOperation() {
        // -1 的原码为1000 0000 0000 0000 0000 0000 0000 0001
        // 补码为1111 1111 1111 1111 1111 1111 1111 1111
        // 左移29为 1110 0000 0000 0000 0000 0000 0000 0000
        // 结果为-2的31次方 + 2的30次方 + 2的29次方: -536870912
        System.out.println(-1 << 29);
        System.out.println(1 << 29);
        System.out.println(2 << 29);
    }
}

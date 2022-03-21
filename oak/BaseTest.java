package oak;

import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Objects;

/**
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
    public void out() {
        byte aByte = (byte) 0b00100001;
        System.out.println(aByte);
        System.out.println(Objects.equals(null, null));
    }
}

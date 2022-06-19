package other;

import java.math.BigDecimal;

/**
 * {@link Double#doubleToLongBits -> doubleToRawLongBits}
 *
 * @author zqw
 * @date 2022/6/17
 * 当构造器使用double或者float则会丢失精度, String或者int型时则不会
 */
public class Q1BigDecimal {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal(88);
        System.out.println(bigDecimal);
        bigDecimal = new BigDecimal("8.8");
        System.out.println(bigDecimal);
        //  new BigDecimal(8.8) -> 8.800000000000000710542735760100185871124267578125
    }
}

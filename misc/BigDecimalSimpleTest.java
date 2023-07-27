package misc;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@link Double#doubleToLongBits -> doubleToRawLongBits}
 * {@link RoundingMode} BigDecimal 舍入模式 共8种
 * {@link RoundingMode#UP} 都会进1
 * {@link RoundingMode#DOWN} 截断操作
 * {@link RoundingMode#HALF_UP} 四舍五入
 * {@link RoundingMode#HALF_DOWN} 五舍六入
 * {@link RoundingMode#CEILING} ? > 0 @see RoundingMode#UP || ? < 0 @see RoundingMode#DOWN
 * {@link RoundingMode#FLOOR} ? > 0 @see RoundingMode#DOWN || ? < 0 @see RoundingMode#UP
 * {@link RoundingMode#HALF_EVEN} ?
 * {@link RoundingMode#UNNECESSARY} ?
 *
 * @author zqw
 * @date 2022/6/17
 */
public class BigDecimalSimpleTest {

    /*当构造器使用double或者float则会丢失精度, String或者int型时则不会*/

    @Test
    public void stringOrIntAsConstrParams() {
        BigDecimal bigDecimal = new BigDecimal(88);
        System.out.println(bigDecimal);
        bigDecimal = new BigDecimal("8.8");
        System.out.println(bigDecimal);
        //  new BigDecimal(8.8) -> 8.800000000000000710542735760100185871124267578125
    }


    /*double类型精度丢失*/

    @Test
    public void lostPrecision() {
        // 加法
        double d1 = 0.11;
        double d2 = 0.1;
        System.out.println(d1 + d2);
        // 减法
        double d3 = 2.11;
        double d4 = 2.10;
        System.out.println(d3 - d4);
        // 乘法
        double d5 = 2.711;
        System.out.println(d5 * 100);
        // 除法
        double d6 = 311.08;
        System.out.println(d6 / 10);

    }

    @Test
    public void usingBigDecimal() {
        // 加减乘除 取余 幂操作 使用 compareTo 比较两个BigDecimal对象大小

        BigDecimal b1 = new BigDecimal("0.11");
        BigDecimal b2 = new BigDecimal("0.1");
        System.out.println(b1.add(b2));

        BigDecimal b3 = new BigDecimal("2.11");
        BigDecimal b4 = new BigDecimal("2.10");
        System.out.println(b3.subtract(b4));


        BigDecimal b5 = new BigDecimal("2.711");
        BigDecimal b6 = new BigDecimal("100");
        System.out.println(b5.multiply(b6));

        BigDecimal b7 = new BigDecimal("311.08");
        BigDecimal b8 = new BigDecimal("10");
        System.out.println(b7.divide(b8, 10, RoundingMode.UP));

        BigDecimal b9 = new BigDecimal("15");
        BigDecimal b10 = new BigDecimal("2");
        System.out.println(b9.remainder(b10));

        BigDecimal b11 = new BigDecimal("3.14");
        System.out.println(b11.pow(3).setScale(2, RoundingMode.HALF_UP));

        System.out.println(b1.compareTo(b2));

    }
}

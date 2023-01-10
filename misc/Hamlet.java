package misc;

import annotation.Pass;

import java.util.Random;

/**
 * --------------- 三目运算符中低精度的数据类型会被强转为高精度 ---------------
 * @author zqw
 * @date 2022/7/16
 */
@Pass
@SuppressWarnings("all")
class Hamlet {
    public static void main(String[] args) {
        Random rnd = new Random();
        boolean toBe = rnd.nextBoolean();
        Number result = (toBe || !toBe) ? new Integer(3) : new Float(1);
        // 3.0
        System.out.println(result);
    }
}

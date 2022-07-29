package other;

import annotation.Pass;

import java.util.Random;

/**
 * @author zqw
 * @date 2022/7/16
 */
@Pass
@SuppressWarnings("all")
class Hamlet {
    public static void main(String[] args) {
        Random rnd = new Random();
        boolean toBe = rnd.nextBoolean();
        // 三目运算符中,低精度的数据类型会被强转为高精度
        Number result = (toBe || !toBe) ? new Integer(3) : new Float(1);
        // 3.0
        System.out.println(result);
    }
}

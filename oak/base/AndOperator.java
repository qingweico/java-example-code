package oak.base;

/**
 * @author zqw
 * @date 2022/12/24
 */
class AndOperator {
    /**
     * &和&&的区别
     */

    /* && : 逻辑与 ,短路与  当第一个条件为false时,第二个条件不会再执行*/
    /* &: 无论第一个条件是否为false,都会执行第二个条件*/

    public static void main(String[] args) {
        int a = 2;
        int b = 4;
        boolean flag = (++a > 3) & (++b > 4);
        // 3
        System.out.println(a);
        // 4
        System.out.println(b);
        // false
        System.out.println(flag);
    }
}

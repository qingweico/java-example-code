package jvm;

import util.Print;

/**
 * @author zqw
 * @date 2021/2/1
 */
class ClassInit {

    private static int num = 1;

    static {
        num = 2;
        number = 20;
        Print.println(num);

        // Illegal forward reference
        // print(number);

    }

    /**
     * linking -> prepare -> number = 0
     */
    private static int number = 10;

    public static void main(String[] args) {
        // 2
        Print.println(num);

        // 10
        Print.println(number);
    }
}


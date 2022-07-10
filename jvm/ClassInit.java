package jvm;

import static util.Print.print;

/**
 * @author zqw
 * @date 2021/2/1
 */
class ClassInit {

    private static int num = 1;

    static {
        num = 2;
        number = 20;
        print(num);

        // Illegal forward reference
        // print(number);

    }

    /**
     * linking -> prepare -> number = 0
     */
    private static int number = 10;

    public static void main(String[] args) {
        // 2
        print(num);

        // 10
        print(number);
    }
}


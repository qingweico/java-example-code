package jvm;

/**
 * @author:qiming
 * @date: 2021/2/3
 */
public class LocalVariables {
    public static void main(String[] args) {
        long num = 10;
        LocalVariables localVariables = new LocalVariables();

        localVariables.test();
    }
    public void test() {
        int a = 0;
        {
            int b = 7;
            b = a + b;
        }
        int c = 4;
    }
    {
        int i = 78;
    }

}

package jvm;


/**
 * @author zqw
 * @date 2021/3/28
 */
public class ReferenceCounting {

    Object reference = null;

    public static void main(String[] args) {
        ReferenceCounting r1 = new ReferenceCounting();
        ReferenceCounting r2 = new ReferenceCounting();

        r1.reference = r2;
        r2.reference = r1;

        r1 = null;
        r2 = null;

        // -Xlog:gc*
        System.gc();
    }
}

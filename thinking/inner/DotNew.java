package thinking.inner;

/**
 * Creating an inner class directly using the .new syntax
 *
 * @author zqw
 * @date 2021/1/30
 */
public class DotNew {
    public int i = 0;

    public class Inner {
        public int i = 2;
    }

    public static void main(String[] args) {
        DotNew dn = new DotNew();
        DotNew.Inner dni = dn.new Inner();
    }
}

package thinking.inner;


import cn.qingweico.io.Print;

/**
 * Qualifying access to the outer-class object
 *
 * @author zqw
 * @date 2021/1/30
 */
public class DoThis {

    void f() {
        Print.println("DoThis.f()");
    }

    public class Inner {
        public DoThis outer() {
            // Generates a reference to an external class object, it
            // is known and checked in the compile time.
            return DoThis.this;
            // A plain "this" would be Inners "this"
        }
    }

    public Inner inner() {
        return new Inner();
    }

    public static void main(String[] args) {
        DoThis dt = new DoThis();
        DoThis.Inner dti = dt.inner();
        dti.outer().f();

    }
}

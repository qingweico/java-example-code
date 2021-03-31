package thinking.inner;


import static util.Print.print;

/**
 * Qualifying access to the outer-class object
 *
 * @author:周庆伟
 * @date: 2021/1/30
 */
public class DoThis {

    void f() {
        print("DoThis.f()");
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

package object;

/**
 * this a good problem about oop from now-coder
 *
 * @author zqw
 * @date 2021/9/20
 */
public class AGoodIssueAboutOop {
    static class A {
        protected int value;

        public A(int v) {
            setValue(v);
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            try {
                value++;
                return value;
            } finally {
                this.setValue(value);
                System.out.println(value);
            }
        }
    }

    static class B extends A {
        public B() {
            super(5);
            setValue(getValue() - 3);
        }

        @Override
        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }

    public static void main(String[] args) {
        // 22 34 17
        System.out.println(new B().getValue());
    }
}

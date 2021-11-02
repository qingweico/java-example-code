package object;

/**
 * @author:qiming
 * @date: 2021/9/20
 */
public class Example {
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

        public void setValue(int value) {
            super.setValue(2 * value);
        }
    }

    public static void main(String[] args) {
        System.out.println(new B().getValue());
    }
}

class C {
    // TODO
    // For the same object, respectively called a b c method, not the
    // same object is locked?
    private synchronized void a() {
    }

    private void b() {
        synchronized (this) {
        }
    }

    private synchronized static void c() {
    }

    private void d() {
        synchronized (C.class) {
        }
    }
}

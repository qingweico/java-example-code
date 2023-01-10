package oak.base;

/**
 * 一个经典的面向对象问题 来源牛客网
 * @author zqw
 * @date 2022/12/24
 */
class ClassicalOopQuestion {
    static class SuperClass {
        protected int value;

        public SuperClass(int v) {
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

    static class BaseClass extends SuperClass {
        public BaseClass() {
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
        System.out.println(new BaseClass().getValue());
    }
}

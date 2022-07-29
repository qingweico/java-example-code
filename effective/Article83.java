package effective;


import annotation.Pass;

/**
 * 慎用延迟初始化
 * In most cases, normal initialization takes precedence over lazy initialization!
 *
 * @author zqw
 * @date 2021/3/24
 */
@Pass
@SuppressWarnings("unused")
class Article83 {
    /**
     * Normal initialization of an instance field
     */
    private final FieldType fieldType = computeFieldValue();


    private FieldType field;

    /**
     * Lazy initialization of instance field - synchronized accessor
     * If you use delay optimization to break the initialized loop,
     * use the synchronous access method.
     *
     * @return FieldType
     */
    private synchronized FieldType getField() {
        if (field == null) {
            field = computeFieldValue();
        }
        return field;
    }
    static class FieldType {
    }

    public static FieldType computeFieldValue() {
        return new FieldType();
    }

    /**Lazy initialization holder class idiom for static fields*/
    private static class FieldHolder {
        static final FieldType FIELD = computeFieldValue();
    }

    private static FieldType field() {
        return FieldHolder.FIELD;
    }

    /**Double-check idiom for lazy initialization of instance field*/
    static class Dcl {
        private volatile FieldType field;

        private FieldType getField() {
            // Using the local variable result can be a little confusing!
            // Although this is not strictly required, it can improve performance!
            // This variable ensures that the Field is read only once if
            // it has already been initialized.
            FieldType result = field;
            if (result == null) {
                synchronized (this) {
                    if (field == null) {
                        result = computeFieldValue();
                        field = result;
                    }
                }
            }
            return result;
        }
    }

    /** Single-check idiom - can cause repeated initialization*/
    static class Scl {
        private volatile FieldType field;

        private FieldType getField() {
            FieldType result = field;
            if (result == null) {
                result = computeFieldValue();
                field = result;
            }
            return result;
        }
    }
}

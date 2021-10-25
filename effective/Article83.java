package effective;


/**
 * 慎用延迟初始化
 *
 * @author:qiming
 * @date: 2021/3/24
 */
// In most cases, normal initialization takes precedence over lazy initialization!
public class Article83 {
    // Normal initialization of an instance field
    private final FieldType fieldType = computeFieldValue();



    private FieldType field;

    // Lazy initialization of instance field - synchronized accessor
    // If you use delay optimization to break the initialized loop,
    // use the synchronous access method.
    private synchronized FieldType getField() {
        if (field == null) {
            field = computeFieldValue();
        }
        return field;
    }

    static class FieldType {}

    public static FieldType computeFieldValue() {
        return new FieldType();
    }

    // Lazy initialization holder class idiom for static fields
    private static class FieldHolder {
        static final FieldType field = computeFieldValue();
    }

    private static FieldType getField0() {
        return FieldHolder.field;
    }

    // Double-check idiom for lazy initialization of instance field.
    static class DCL {
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

    // Single-check idiom - can cause repeated initialization
    static class SCL {
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

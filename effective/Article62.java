package effective;

/**
 * 如果其他类型更适合, 则尽量避免使用字符串
 *
 * @author zqw
 * @date 2021/11/25
 * @see java.lang.ThreadLocal
 */
class Article62 {
    // Inappropriate use of string aggregate type
    // String compoundKey = className + "#" + i.next;

    public static void main(String[] args) {

    }
}

/**
 * Broken - inappropriate use of string as capability!
 */
class ThreadLocal {

    /**
     * Non- instantiable
     */
    private ThreadLocal() {

    }
    // Sets the current thread's value for the named variables.

    /**
     * key not unique!
     */
    public static void set(String key, Object value) {

    }

    /**
     * Returns the current thread's value for the named variables
     */
    public static void get(String key) {
    }


    /**
     * --------------revise --------------
     */
    public static class Key {
        Key() {
        }
    }

    /**
     * Generates a unique, un-forgettable key
     */
    public static Key getKey() {
        return new Key();
    }

    public static void set(Key key, Object value) {

    }

    public static void get(Key key) {
    }
}

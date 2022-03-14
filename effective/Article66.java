package effective;

/**
 * 谨慎地使用本地方法
 * Java Native Interface (JNI) allows Java applications to call native methods,
 * native methods provide a platform-specific mechanism for accessing them.
 *
 * @author zqw
 * @date 2021/2/19
 */
class Article66 {
    // The native language is insecure and can expose the program to memory corruption.
    // Because the native language is platform-specific, applications that use native
    // methods are no longer freely portable.
    // Applications that use native methods are also harder to debug

    public native static void sleep();

    public static void main(String[] args) {
        sleep();
        // java.lang.UnsatisfiedLinkError
        // The JVM could not find the corresponding local library file
    }
}

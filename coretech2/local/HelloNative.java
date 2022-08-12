package coretech2.local;

/**
 * @author zqw
 * @date 2022/8/9
 */
class HelloNative {
    public static native void greeting();
    public static native void greeting(long i);

    static {
        System.loadLibrary("HelloNative");
    }

    public static void main(String[] args) {
        greeting();
    }
}


// [Windows Environment]
// Generate `h` header file: Root directory: javac -h coretech2/local/ coretech2/local/HelloNative.java
// gcc -I ${jdk_home}\include\win32 -LD HelloNative.c -FeHelloNative.dll

package coretech2.local;

/**
 * @author zqw
 * @date 2022/8/9
 */
class HelloNative {
    public static native void greeting();

    static {
        System.loadLibrary("HelloNative");
    }
}

// Windows cl -I ${jdk_home}\include\win32 -LD HelloNative.c -FeHelloNative.dll

package coretech2.local;

/**

 *
 * @author zqw
 * @date 2022/8/9
 */
public class HelloNative {

    int m = 10;
    public native void instance();

    public static native void greeting();

    public static native void greeting(long i);

    static {
        System.loadLibrary("HelloNative");
    }

    public static void main(String[] args) {
        HelloNative hn = new HelloNative();
        hn.instance();
        greeting();
        greeting(1);
    }
}


// [Windows Environment]
// Generate `h` header file: Root directory: javac -h coretech2/local/ coretech2/local/HelloNative.java
// gcc -I ${jdk_home}\include\win32 -LD HelloNative.c -FeHelloNative.dll

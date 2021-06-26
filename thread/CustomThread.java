package thread;

/**
 * @author:qiming
 * @date: 2021/6/25
 */
public class CustomThread {

    static {
        System.loadLibrary("threadNative");
    }

    public static void main(String[] args) {
        CustomThread ct = new CustomThread();
        ct.start0();

    }
    // Using JNI
    private native void start0();

}

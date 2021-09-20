package thread;

/**
 *  // Thread.start() ----->  see openjdk
 *      thread.cpp>>>JavaThread*>>>os::create_thread
 *
 * @author:qiming
 * @date: 2021/6/25
 */
public class CustomThread {

    static {
        System.loadLibrary("CustomThreadNative");
    }

    public static void main(String[] args) {
        CustomThread ct = new CustomThread();
        ct.start0();
    }

    // Using JNI
    private native void start0();

}


// /home/java (copy CustomThread.java to this directory, include the package name)


// 1: javah thread.CustomThread   -----> thread_CustomThread.h

// see thead.new.c
// 2: gcc -fPIC -I /usr/local/jdk8/include/linux -I /usr/local/jdk8/include/linux
//        -shared -o libCustomThreadNative.so thread_new.c   -----> libCustomThreadNative.so


// 3: export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/java

// 4: cd thread        javac CustomThread.java

// 5: cd ../           java thread.CustomThread



